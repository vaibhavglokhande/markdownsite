package com.markdownsite.integration.models;

/**
 * The interface Rule validator.
 * The implementation of this interface validates the values as per the configured rule value.
 *
 * @param <T> the type parameter
 */
public interface RuleValidator<T> {

    /**
     * Validate the rule value.
     *
     * @param propertyValue the property value
     * @param ruleValue     the rule value
     * @return the boolean
     */
    boolean validate(T propertyValue, T ruleValue);
}
