package com.markdownsite.core.utility;

import com.markdownsite.core.FileNode;
import com.markdownsite.integration.models.SimpleTree;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type File based source utility.
 */
public class FileBasedSourceUtility {
    private SimpleTree<FileNode, File> fileSimpleTree;

    /**
     * Build tree simple tree.
     *
     * @param rootDirectoryPath the root directory path - The path to the source directory or classpath directory
     * @param readFromClasspath the read from classpath - If set to true, the utility will optionally search for source in the classpath if provided path not found
     * @param extensions        the extensions
     * @return the simple tree
     */
    public SimpleTree<FileNode, File> buildTree(String rootDirectoryPath, boolean readFromClasspath, String... extensions) {
        File rootDirectory = new File(rootDirectoryPath);
        if(!rootDirectory.exists() && readFromClasspath) {
            ClassPathResource classPathResource = new ClassPathResource(rootDirectoryPath);
            try {
                rootDirectory = classPathResource.getFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileNode rootNode = getFileNode(rootDirectory);
        fileSimpleTree = new SimpleTree<>(rootNode);
        buildSubTree(rootNode, extensions);
        return fileSimpleTree;
    }

    private void buildSubTree(FileNode fileNode, String... extensions) {
        List<File> filesWithExtension = getFilesWithExtension(fileNode.getValue(), extensions);
        // Add all the files with allowed extension.
        for (File childFile : filesWithExtension) {
            FileNode childNode = getFileNode(childFile);
            fileSimpleTree.addNode(fileNode, childNode);
        }
        List<File> childDirectories = getChildDirectories(fileNode.getValue());
        for (File childDirectory : childDirectories) {
            FileNode directoryNode = getFileNode(childDirectory);
            fileSimpleTree.addNode(fileNode, directoryNode);
            buildSubTree(directoryNode, extensions);
        }
    }

    private FileNode getFileNode(File childFile) {
        FileNode childNode = new FileNode();
        childNode.setIdentifier(UUID.nameUUIDFromBytes(childFile.getName().getBytes(StandardCharsets.UTF_8)).toString());
        childNode.setValue(childFile);
        return childNode;
    }

    private List<File> getChildDirectories(File file) {
        File[] array = file.listFiles(File::isDirectory);
        if(array != null)
            return Arrays.stream(array).toList();
        return Collections.emptyList();
    }

    private List<File> getFilesWithExtension(File file, String... extensions) {
        try (Stream<Path> walk = Files.walk(Paths.get(file.toURI()), 1)) {
            return walk.filter(path -> !Files.isDirectory(path))
                    .filter(path -> isEndWith(path.getFileName().toString(), extensions))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            // TODO Add logging.
        }
        return Collections.emptyList();
    }

    private boolean isEndWith(String file, String... fileExtensions) {
        boolean result = false;
        for (String fileExtension : fileExtensions) {
            if (file.endsWith(fileExtension)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
