package com.markdownsite.integration.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * The type Configurable properties.
 *
 * @param <T> the type parameter
 */
@Data
public class ConfigurablePropertiesRules<T> {
    /**
     * The allowed rules for the property.
     */
    private List<Rule<T>> rules;
    /**
     * The Allow custom.
     */
    private boolean allowCustom;


    /**
     * Instantiates a new Configurable properties rules.
     *
     * @param allowCustom the allow custom
     * @param rules       the rules
     */
    public ConfigurablePropertiesRules(boolean allowCustom, List<Rule<T>> rules) {
        this.rules = rules;
        this.allowCustom = allowCustom;
    }

    /**
     * Instantiates a new Configurable properties rules.
     * The default behaviour allows custom values also.
     */
    public ConfigurablePropertiesRules() {
        this.allowCustom = true;
    }
}
