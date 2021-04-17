package com.markdownsite.integration.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DefaultConfigurablePropertiesValidatorTest {
    @Test
    void testValidateProperty() {
        ConfigurablePropertiesValidator<String> propertiesValidator = new DefaultConfigurablePropertiesValidator();
        assertTrue(propertiesValidator.validateProperty("rule-1", getRulesDenyCustom()));
        assertTrue(propertiesValidator.validateProperty("rule-2", getRulesDenyCustom()));
        assertFalse(propertiesValidator.validateProperty("custom-rule", getRulesDenyCustom()));
    }

    @Test
    void testValidateAllowCustom() {
        ConfigurablePropertiesValidator<String> propertiesValidator = new DefaultConfigurablePropertiesValidator();
        assertTrue(propertiesValidator.validateProperty("custom-rule", new ConfigurablePropertiesRules<String>(true, Arrays.asList(new Rule<>("rule-1", new TestStringValidator())))));
    }


    @Test
    void testValidateDefault() {
        ConfigurablePropertiesValidator<String> propertiesValidator = new DefaultConfigurablePropertiesValidator();
        assertTrue(propertiesValidator.validateProperty("custom", new ConfigurablePropertiesRules<>()));
    }

    private ConfigurablePropertiesRules<String> getRulesDenyCustom() {
        TestStringValidator ruleValidator = new TestStringValidator();
        return new ConfigurablePropertiesRules<>(false, Arrays.asList(new Rule<>("rule-1", ruleValidator), new Rule<>("rule-2", ruleValidator)));
    }

    static class TestStringValidator implements RuleValidator<String> {
        @Override
        public boolean validate(String propertyValue, String ruleValue) {
            return ruleValue.equals(propertyValue);
        }
    }
}