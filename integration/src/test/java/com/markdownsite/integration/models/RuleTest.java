package com.markdownsite.integration.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {
    @Test
    void testValidate() {
        Rule<Integer> integerRule = new Rule<>(10, (propertyValue, ruleValue) -> propertyValue >= ruleValue);

        assertFalse(integerRule.validate(4));
        assertTrue(integerRule.validate(10));

    }
}