package com.markdownsite.integration.models;

/**
 * The validator for the String type values.
 */
public class StringRuleValidator implements RuleValidator<String> {

    private boolean ignoreCase;

    /**
     * Instantiates a new String rule validator.<br>
     * The ignore case property is set to false.
     */
    public StringRuleValidator() {
        this.ignoreCase = false;
    }

    /**
     * Instantiates a new String rule validator.
     *
     * @param ignoreCase the ignore case
     */
    public StringRuleValidator(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    @Override
    public boolean validate(String propertyValue, String ruleValue) {
        if (ignoreCase)
            return ruleValue.equalsIgnoreCase(propertyValue);
        return ruleValue.equals(propertyValue);
    }
}
