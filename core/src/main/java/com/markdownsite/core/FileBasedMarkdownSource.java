package com.markdownsite.core;

import com.markdownsite.core.enums.FileBasedMarkdownSourceErrorCode;
import com.markdownsite.core.exceptions.FileBasedMarkdownSourceException;
import com.markdownsite.core.utility.FileBasedSourceUtility;
import com.markdownsite.integration.enums.PropertyValidationErrorCode;
import com.markdownsite.integration.exceptions.PropertyValidationException;
import com.markdownsite.integration.exceptions.TreeOperationException;
import com.markdownsite.integration.interfaces.NavigableMarkdownSource;
import com.markdownsite.integration.interfaces.SimpleTraverseMode;
import com.markdownsite.integration.interfaces.Tree;
import com.markdownsite.integration.models.*;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileBasedMarkdownSource implements NavigableMarkdownSource {

    public static final String ALLOWED_SOURCE_EXTENSION = ".md";
    private SimpleTree<FileNode, File> fileSimpleTree;
    private ConfigurablePropertiesValidator propertiesValidator = new DefaultConfigurablePropertiesValidator();

    private static final String IDENTIFIER = FileBasedMarkdownSource.class.getSimpleName();
    public static final String PROPERTY_SOURCE_DIR = "FileSource.sourceDirectory";
    public static final String READ_FROM_CLASSPATH = "FileSource.readFromClasspath";
    private List<SourceProviderConfigProperty> sourceProviderConfig = new ArrayList<>();
    private Map<String, MarkdownElement<String>> markdownSourceMap = new ConcurrentHashMap<>();
    private Tree<SourceNavigationNode, String> navigationTree;
    @Setter
    private String sourceName;
    @Setter
    private String sourceDescription;

    public FileBasedMarkdownSource(String sourceName) {
        this(sourceName,"");
    }

    public FileBasedMarkdownSource(String sourceName, String sourceDescription) {
        this.sourceName = sourceName;
        this.sourceDescription = sourceDescription;
        configureDefaultProperties();
    }

    @Override
    public void initializeSource() throws FileBasedMarkdownSourceException, TreeOperationException {
        SourceProviderConfigProperty sourceDirectoryProperty = sourceProviderConfig.stream().filter(property -> property.getPropertyName().equals(FileBasedMarkdownSource.PROPERTY_SOURCE_DIR)).findFirst().orElse(null);
        if (sourceDirectoryProperty == null)
            throw new FileBasedMarkdownSourceException(FileBasedMarkdownSourceErrorCode.SOURCE_DIRECTORY_NOT_CONFIGURED);
        SourceProviderConfigProperty<Boolean> readFromClassPathProperty = sourceProviderConfig.stream().filter(property -> property.getPropertyName().equalsIgnoreCase(READ_FROM_CLASSPATH)).findFirst().orElse(null);
        boolean readFromClasspath = false;
        if (readFromClassPathProperty != null)
            readFromClasspath = readFromClassPathProperty.getPropertyValue();
        FileBasedSourceUtility fileBasedSourceUtility = new FileBasedSourceUtility();
        this.fileSimpleTree = fileBasedSourceUtility.buildTree((String) sourceDirectoryProperty.getPropertyValue(), readFromClasspath, ALLOWED_SOURCE_EXTENSION);
        buildSource();
        buildNavigationTree();
    }

    private void buildNavigationTree() throws TreeOperationException {
        navigationTree = fileSimpleTree.convert(fileNode -> {
            SourceNavigationNode navigationNode = new SourceNavigationNode();
            navigationNode.setDirectory(fileNode.getValue().isDirectory());
            String identifier = getIdentifier(fileNode);
            navigationNode.setValue(identifier);
            navigationNode.setDisplayName(fileNode.getValue().getName());
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
            if (!value.isDirectory())
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
    public List<SourceProviderConfigProperty> getSourceConfig() {
        return sourceProviderConfig;
    }

    @Override
    public void updateSourceConfig(List<SourceProviderConfigProperty> sourceProviderConfig) throws PropertyValidationException {
        validateProperties(sourceProviderConfig);
        this.sourceProviderConfig = sourceProviderConfig;
    }

    @Override
    public String sourceIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public String sourceName() {
        return sourceName;
    }

    @Override
    public String sourceDescription() {
        return this.sourceDescription;
    }

    private void validateProperties(List<SourceProviderConfigProperty> sourceProviderConfig) throws PropertyValidationException {
        for (SourceProviderConfigProperty entry : sourceProviderConfig) {
            boolean validation = this.propertiesValidator.validateProperty(entry.getPropertyValue(), entry.getConfigurablePropertiesRules());
            if (!validation)
                throw new PropertyValidationException("Property value for " + entry.getPropertyName() + " is invalid", PropertyValidationErrorCode.INVALID_PROPERTY);

        }
    }

    @Override
    public Tree<SourceNavigationNode, String> getNavigationTree() {
        return navigationTree;
    }

    public void configureDefaultProperties() {
        SourceProviderConfigProperty<String> sourceProviderConfigProperty = new SourceProviderConfigProperty<>(PROPERTY_SOURCE_DIR, "");
        sourceProviderConfig.add(sourceProviderConfigProperty);
        SourceProviderConfigProperty<Boolean> readFromClassPathConfigProperty = new SourceProviderConfigProperty<>(READ_FROM_CLASSPATH, false);
        sourceProviderConfig.add(readFromClassPathConfigProperty);
    }
}
