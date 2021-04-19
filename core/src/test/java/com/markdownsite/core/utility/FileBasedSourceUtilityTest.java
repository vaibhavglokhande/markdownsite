package com.markdownsite.core.utility;

import com.markdownsite.core.FileNode;
import com.markdownsite.integration.models.SimpleTree;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileBasedSourceUtilityTest {

    @Test
    void buildTree() {
        Path sourceDirectory = Paths.get("src", "test", "resources", "markdown-files");
        FileBasedSourceUtility fileBasedSourceUtility = new FileBasedSourceUtility();
        SimpleTree<FileNode, File> simpleTree = fileBasedSourceUtility.buildTree(sourceDirectory.toString(), ".md");
        assertNotNull(simpleTree);
        assertEquals(2, simpleTree.getChildren(simpleTree.getRootNode()).size());
    }

}