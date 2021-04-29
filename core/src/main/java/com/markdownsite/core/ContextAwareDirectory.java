package com.markdownsite.core;

import com.markdownsite.integration.interfaces.ContextAware;
import com.markdownsite.integration.interfaces.ContextAwareEventListener;
import lombok.Data;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * {@inheritDoc}
 * This class provides context aware directory.
 * It uses a WatcherService to monitor the directory and raises an event when change takes place.
 */
public class ContextAwareDirectory implements ContextAware<String, WatchEvent> {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void setupAwareness(Supplier<String> supplier, ContextAwareEventListener<WatchEvent> awareEventListener) {
        String rootDirectoryPath = supplier.get();
        try {
            var watchServiceThread = new WatchServiceThread(rootDirectoryPath, awareEventListener);
            executorService.submit(watchServiceThread);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class WatchServiceThread implements Runnable {

        private final Map<WatchKey, Path> watchEventPathMap = new ConcurrentHashMap<>();
        private final WatchService watchService;
        private final ContextAwareEventListener<WatchEvent> awareEventListener;

        WatchServiceThread(String rootDirectory, ContextAwareEventListener<WatchEvent> awareEventListener) throws IOException {
            watchService = FileSystems.getDefault().newWatchService();
            this.awareEventListener = awareEventListener;
            var path = Paths.get(rootDirectory);
            registerAll(path);
        }

        private void registerDirectory(Path path) throws IOException {
            var watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
            watchEventPathMap.put(watchKey, path);
        }

        private void registerAll(Path rootPath) throws IOException {
            Files.walkFileTree(rootPath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    registerDirectory(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        }

        @Override
        public void run() {
            WatchKey key = null;
            while (true) {
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                Path path = watchEventPathMap.get(key);
                if(path == null)
                    continue;
                List<WatchEvent<?>> watchEvents = key.pollEvents();
                for (WatchEvent watchEvent : watchEvents) {
                    if (awareEventListener != null)
                        awareEventListener.listenEvent(watchEvent);
                    WatchEvent.Kind kind = watchEvent.kind();
                    if(kind == StandardWatchEventKinds.ENTRY_CREATE){
                        WatchEvent<Path> pathWatchEvent = watchEvent;
                        var registeredPath = watchEventPathMap.get(key);
                        var created = pathWatchEvent.context();
                        var childPath = registeredPath.resolve(created);
                        if(Files.isDirectory(childPath)) {
                            try {
                                // Register all children of newly created directory.
                                registerAll(childPath);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                boolean validKey = key.reset();
                // If key is no longer valid, remove it.
                if(!validKey)
                    watchEventPathMap.remove(key);
            }
        }
    }

    @Data
    public static class ContextAwareDirectoryEvent {
        private WatchEvent<?> watchEvent;
    }
}
