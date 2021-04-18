package com.markdownsite.core;

import com.markdownsite.core.exceptions.FileBasedMarkdownSourceException;
import com.markdownsite.integration.enums.PropertyValidationErrorCode;
import com.markdownsite.integration.exceptions.PropertyValidationException;
import com.markdownsite.integration.interfaces.MarkdownSource;
import com.markdownsite.integration.models.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileBasedMarkdownSource implements MarkdownSource<String> {

    public static final String ALLOWED_SOURCE_EXTENSION = ".md";
    private SimpleTree<FileNode, File> fileSimpleTree;
    private ConfigurablePropertiesValidator<String> propertiesValidator = new DefaultConfigurablePropertiesValidator<>();

    public static final String IDENTIFIER = FileBasedMarkdownSource.class.getName();
    public static final String PROPERTY_SOURCE_DIR = "sourceDirectory";
    private Map<String, SourceProviderConfigProperty<String>> sourceProviderConfig = new ConcurrentHashMap<>();


    @Override
    public void initializeSource() throws FileBasedMarkdownSourceException {
        SourceProviderConfigProperty<String> sourceDirectoryProperty = sourceProviderConfig.get(PROPERTY_SOURCE_DIR);
        if (sourceDirectoryProperty == null || sourceDirectoryProperty.getPropertyValue() == null)
            throw new FileBasedMarkdownSourceException(com.markdownsite.core.enums.FileBasedMarkdownSourceException.SOURCE_DIRECTORY_NOT_CONFIGURED);
        buildTree(sourceDirectoryProperty.getPropertyValue());
    }

    private void buildTree(String rootDirectoryPath) {
        File rootDirectory = new File(rootDirectoryPath);
        FileNode rootNode = getFileNode(rootDirectory);
        fileSimpleTree = new SimpleTree<>(rootNode);
        buildSubTree(rootNode);

    }

    private void buildSubTree(FileNode fileNode) {
        List<File> filesWithExtension = getFilesWithExtension(fileNode.getValue(), ALLOWED_SOURCE_EXTENSION);
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

    @Override
    public MarkdownElement<String> getMarkdownElement(String identifier) {
        return null;
    }

    @Override
    public Map<String, MarkdownElement<String>> getAll() {
        return null;
    }

    @Override
    public Map<String, SourceProviderConfigProperty<String>> getSourceConfig() {
        return sourceProviderConfig;
    }

    @Override
    public void updateSourceConfig(Map<String, SourceProviderConfigProperty> sourceProviderConfig) throws PropertyValidationException {
        ConcurrentHashMap mapToUpdate = new ConcurrentHashMap<>(sourceProviderConfig);
        validateProperties(mapToUpdate);
        this.sourceProviderConfig = mapToUpdate;
    }

    @Override
    public String sourceIdentifier() {
        return IDENTIFIER;
    }

    public void configFileSource(String rootDirectory) {
        SourceProviderConfigProperty<String> sourceProviderConfigProperty = new SourceProviderConfigProperty<>(PROPERTY_SOURCE_DIR, rootDirectory);
        sourceProviderConfig.put(PROPERTY_SOURCE_DIR, sourceProviderConfigProperty);
    }

    private void validateProperties(Map<String, SourceProviderConfigProperty<String>> sourceProviderConfig) throws PropertyValidationException {
        for (Map.Entry<String, SourceProviderConfigProperty<String>> entry : sourceProviderConfig.entrySet()) {
            SourceProviderConfigProperty<String> sourceProviderConfigProperty = entry.getValue();
            boolean validation = this.propertiesValidator.validateProperty(sourceProviderConfigProperty.getPropertyValue(), sourceProviderConfigProperty.getConfigurablePropertiesRules());
            if (!validation)
                throw new PropertyValidationException("Property value for " + sourceProviderConfigProperty.getPropertyName() + " is invalid", PropertyValidationErrorCode.INVALID_PROPERTY);

        }
    }
}
