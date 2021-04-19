package com.markdownsite.core.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.markdownsite.core.FileBasedMarkdownSource;
import com.markdownsite.core.FileNode;
import com.markdownsite.integration.models.SimpleTree;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class FileBasedSourceUtilityTest {

    @Test
    void buildTree() {
        Path sourceDirectory = Paths.get("src", "test", "resources", "markdown-files");
        FileBasedSourceUtility fileBasedSourceUtility = new FileBasedSourceUtility();
        SimpleTree<FileNode, File> simpleTree = fileBasedSourceUtility.buildTree(sourceDirectory.toString(), ".md");
        assertNotNull(simpleTree);
        assertEquals(2, simpleTree.getChildren(simpleTree.getRootNode()).size());
    }

    @Test
    void name() {
        FileBasedSourceUtilityTest fileBasedSourceUtilityTest = new FileBasedSourceUtilityTest();
        String func = fileBasedSourceUtilityTest.func(fileNode -> fileNode.getIdentifier() != null);
        System.out.println(func);
    }

    private String func(Predicate<FileNode> fileNodePredicate){
        boolean test = fileNodePredicate.test(new FileNode());
        if(test)
        return "Yes";
        return "No";
    }
}