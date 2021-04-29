package com.markdownsite.integration.interfaces;

/**
 * The type Context aware event listener.
 * The event listener for context aware classes.
 *
 * @param <T> the type parameter -  The type of event generated.
 */
public interface ContextAwareEventListener<T> {

    /**
     * Listen event.
     *
     * @param event the event
     */
    void listenEvent(T event);

}
