package com.markdownsite.integration.models;

import lombok.Data;

@Data
public class Rule<T> implements Validation<T> {

    private T ruleValue;
    private RuleValidator<T> ruleValidator;

    public Rule(T ruleValue, RuleValidator<T> ruleValidator) {
        this.ruleValue = ruleValue;
        this.ruleValidator = ruleValidator;
    }

    @Override
    public boolean validate(T value) {
        return this.ruleValidator.validate(value, ruleValue);
    }
}
