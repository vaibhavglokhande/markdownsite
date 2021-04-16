package com.markdownsite.integration.models;

import lombok.Data;

@Data
public class MarkdownAttribute<T> {

    private String key;
    private T value;

}
