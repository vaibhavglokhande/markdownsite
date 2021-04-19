package com.markdownsite.core.utility;

import com.markdownsite.core.FileNode;
import com.markdownsite.integration.models.SimpleTree;

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

public class FileBasedSourceUtility {
    private SimpleTree<FileNode, File> fileSimpleTree;

    public SimpleTree<FileNode, File> buildTree(String rootDirectoryPath, String... extensions) {
        File rootDirectory = new File(rootDirectoryPath);
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
            buildSubTree(directoryNode);
        }
    }

    private FileNode getFileNode(File childFile) {
        FileNode childNode = new FileNode();
        childNode.setIdentifier(UUID.nameUUIDFromBytes(childFile.getName().getBytes(StandardCharsets.UTF_8)).toString());
        childNode.setValue(childFile);
        return childNode;
    }

    private List<File> getChildDirectories(File file) {
        return Arrays.stream(file.listFiles(File::isDirectory)).toList();
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
