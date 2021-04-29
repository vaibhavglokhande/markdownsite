package com.markdownsite.integration.interfaces;

import java.util.function.Supplier;

/**
 * The interface Context aware.
 * The classes that implement this interface are aware of the changes in the data within the class.
 *
 * @param <T> the type parameter - specifies the type of data required by the APIs to setup the context awareness in the class
 * @param <E> the type parameter - specfifies the type of event raised.
 */
public interface ContextAware<T, E> {

    /**
     * Sets awareness.
     * This API setup the awareness feature in the class.
     *
     * @param supplier           the supplier  - The supplier provides the required data to the api.
     * @param awareEventListener the aware event listener
     */
    void setupAwareness(Supplier<T> supplier, ContextAwareEventListener<E> awareEventListener);
}
