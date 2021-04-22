package com.markdownsite.core;

import com.markdownsite.integration.models.SourceProviderConfigProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
        "com.markdownsite.core.FileBasedMarkdownSource.sourceDirectory=core/src/test/resources/markdown-files",
        "com.test.int=52"
})
class PropertyFileSourceConfigInitializerTest {

    @Autowired
    private PropertyFileSourceConfigInitializer propertyFileSourceConfigInitializer;

    @Test
    void testSourceConfig() {
        SourceProviderConfigProperty property1 = new SourceProviderConfigProperty("com.markdownsite.core.FileBasedMarkdownSource.sourceDirectory","");
        SourceProviderConfigProperty property2 = new SourceProviderConfigProperty("com.test.int", 0);

        List<SourceProviderConfigProperty> sourceProviderConfigProperties = new ArrayList<>(){{
            add(property1);
            add(property2);
        }};


        List<SourceProviderConfigProperty> updatedConfig = propertyFileSourceConfigInitializer.initProperties(sourceProviderConfigProperties);
        assertEquals(2, updatedConfig.size());
    }

    @TestConfiguration
    static class PropertyFileSourceConfigInitializerTestConfig{
        @Autowired
        private Environment environment;
        @Bean
        public PropertyFileSourceConfigInitializer getPropertyFileSourceConfigInitializer(){
            return new PropertyFileSourceConfigInitializer(environment);
        }

    }
}