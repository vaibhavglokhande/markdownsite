package com.markdownsite.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class DefaultResourceProviderTest {

    @Test
    void getJsResources() {
    }

    @Test
    void getCssResources() {
    }

    @Test
    void getInlineJsResources() {
    }

    @Test
    void getInlineCssResources() {
    }

    @TestConfiguration
    static class DefaultResourceProviderTestConfig{

    }
}