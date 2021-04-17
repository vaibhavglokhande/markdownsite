package com.markdownsite.integration.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringRuleValidatorTest {

    @Test
    void validateCaseSensitive() {
        Rule<String> stringRule = new Rule<>("allowedValue", new StringRuleValidator());
        assertTrue(stringRule.validate("allowedValue"));
        assertFalse(stringRule.validate("allowedvalue"));
    }

    @Test
    void validateCaseInSensitive() {
        Rule<String> stringRule = new Rule<>("allowedValue", new StringRuleValidator(true));
        assertTrue(stringRule.validate("allowedValue"));
        assertTrue(stringRule.validate("allowedvalue"));
    }
}