package com.markdownsite.core;

import com.markdownsite.integration.models.Node;
import lombok.Data;

import java.io.File;

@Data
public class FileNode extends Node<File> {
    private String identifier;
}
