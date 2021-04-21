package com.markdownsite.core;

import com.markdownsite.core.exceptions.FileBasedMarkdownSourceException;
import com.markdownsite.core.utility.FileBasedSourceUtility;
import com.markdownsite.integration.enums.PropertyValidationErrorCode;
import com.markdownsite.integration.exceptions.PropertyValidationException;
import com.markdownsite.integration.exceptions.TreeOperationException;
import com.markdownsite.integration.interfaces.NavigableMarkdownSource;
import com.markdownsite.integration.interfaces.SimpleTraverseMode;
import com.markdownsite.integration.interfaces.Tree;
import com.markdownsite.integration.models.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileBasedMarkdownSource implements NavigableMarkdownSource<String, String> {

    public static final String ALLOWED_SOURCE_EXTENSION = ".md";
    private SimpleTree<FileNode, File> fileSimpleTree;
    private ConfigurablePropertiesValidator<String> propertiesValidator = new DefaultConfigurablePropertiesValidator<>();

    public static final String IDENTIFIER = FileBasedMarkdownSource.class.getName();
    public static final String PROPERTY_SOURCE_DIR = "sourceDirectory";
    private Map<String, SourceProviderConfigProperty<String>> sourceProviderConfig = new ConcurrentHashMap<>();
    private Map<String, MarkdownElement<String>> markdownSourceMap = new ConcurrentHashMap<>();
    private Tree<SourceNavigationNode<String>, String> navigationTree;


    @Override
    public void initializeSource() throws FileBasedMarkdownSourceException, TreeOperationException {
        SourceProviderConfigProperty<String> sourceDirectoryProperty = sourceProviderConfig.get(PROPERTY_SOURCE_DIR);
        if (sourceDirectoryProperty == null || sourceDirectoryProperty.getPropertyValue() == null)
            throw new FileBasedMarkdownSourceException(com.markdownsite.core.enums.FileBasedMarkdownSourceException.SOURCE_DIRECTORY_NOT_CONFIGURED);
        FileBasedSourceUtility fileBasedSourceUtility = new FileBasedSourceUtility();
        this.fileSimpleTree = fileBasedSourceUtility.buildTree(sourceDirectoryProperty.getPropertyValue(), ALLOWED_SOURCE_EXTENSION);
        buildSource();
        buildNavigationTree();
    }

    private void buildNavigationTree() throws TreeOperationException {
        navigationTree = fileSimpleTree.convert(fileNode -> {
            SourceNavigationNode<String> navigationNode = new SourceNavigationNode<>();
            navigationNode.setDirectory(fileNode.getValue().isDirectory());
            String identifier = getIdentifier(fileNode);
            navigationNode.setValue(identifier);
            navigationNode.setDisplayName(fileNode.getValue().getName());
            navigationNode.setMarkdownElement(getMarkdownElement(identifier));
            return navigationNode;
        });
    }

    private void buildSource() throws TreeOperationException {
        List<FileNode> fileNodes = fileSimpleTree.traverse(fileSimpleTree.getRootNode(), SimpleTraverseMode.BREADTH_FIRST);
        for (FileNode fileNode : fileNodes) {
            MarkdownElement<String> markdownElement = new MarkdownElement<>();
            markdownElement.setTitle(fileNode.getValue().getName());
            String identifier = getIdentifier(fileNode);
            markdownElement.setIdentifier(identifier);
            File value = fileNode.getValue();
            try (Stream<String> lines = Files.lines(value.toPath())) {
                String content = lines.collect(Collectors.joining(System.lineSeparator()));
                markdownElement.setContent(content);
            } catch (IOException e) {
                e.printStackTrace();
                //TODO Add logging
            }
            markdownSourceMap.put(identifier, markdownElement);
        }
    }

    private String getIdentifier(FileNode fileNode) {
        return UUID.nameUUIDFromBytes(fileNode.getValue().getAbsolutePath().getBytes(StandardCharsets.UTF_8)).toString();
    }

    @Override
    public MarkdownElement<String> getMarkdownElement(String identifier) {
        return markdownSourceMap.get(identifier);
    }

    @Override
    public Map<String, MarkdownElement<String>> getAll() {
        return markdownSourceMap;
    }

    @Override
    public Map<String, SourceProviderConfigProperty<String>> getSourceConfig() {
        return sourceProviderConfig;
    }

    @Override
    public void updateSourceConfig(Map<String, SourceProviderConfigProperty<String>> sourceProviderConfig) throws PropertyValidationException {
        validateProperties(sourceProviderConfig);
        this.sourceProviderConfig = sourceProviderConfig;
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

    @Override
    public Tree<SourceNavigationNode<String>, String> getNavigationTree() {
        return navigationTree;
    }
}
