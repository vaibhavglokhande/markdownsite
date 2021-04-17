package com.markdownsite.integration.models;

/**
 * The interface Validation.
 * The implementation of this interface provides the api to validate any property as per it's requirements.
 *
 * @param <T> the type parameter
 */
public interface Validation<T> {
    /**
     * Validate the provided value as per the implementation requirements.
     *
     * @param value the value
     * @return the boolean
     */
    boolean validate(T value);
}
