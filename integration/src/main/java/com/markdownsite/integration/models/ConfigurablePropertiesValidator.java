package com.markdownsite.integration.models;

/**
 * The interface Configurable properties validator.
 * The implementation of this interface provides the validation for the properties that can be used as per the configuration.
 *
 * @param <T> the type parameter
 */
public interface ConfigurablePropertiesValidator<T> {

    /**
     * Validate property boolean.
     *
     * @param property                    the property value to be validated.
     * @param configurablePropertiesRules the configurable properties rules, the rules which are applied to the property value.
     * @return the boolean
     */
    boolean validateProperty(T property, ConfigurablePropertiesRules<T> configurablePropertiesRules);
}
