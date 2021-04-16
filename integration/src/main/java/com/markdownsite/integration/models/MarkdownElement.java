package com.markdownsite.integration.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MarkdownElement<T> {

    private T identifier;
    private String title;
    private String content;
    private List<MarkdownAttribute<?>> markdownAttributes = new ArrayList<>();
}
