package com.markdownsite.integration.models;

import java.util.Optional;

public class DefaultConfigurablePropertiesValidator<T> implements ConfigurablePropertiesValidator<T> {
    @Override
    public boolean validateProperty(T property, ConfigurablePropertiesRules<T> configurablePropertiesRules) {
        // Allow all the values if no rules are in place.
        if (configurablePropertiesRules == null || configurablePropertiesRules.getRules() == null)
            return true;
        // If custom values are allowed, do not apply the rule.
        if (configurablePropertiesRules.isAllowCustom())
            return true;

        // If any of the rules matches.
        Optional<Rule<T>> matchedRule = configurablePropertiesRules.getRules().stream().filter(rule -> rule.validate(property)).findFirst();

        return matchedRule.isPresent();
    }
}
