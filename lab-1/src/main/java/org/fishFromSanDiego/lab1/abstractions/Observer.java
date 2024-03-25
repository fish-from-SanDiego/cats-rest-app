package org.fishFromSanDiego.lab1.abstractions;

/**
 * The interface Observer.
 *
 * @param <TValue> the type parameter
 */
public interface Observer<TValue> {
    /**
     * Accept notification.
     *
     * @param value the value
     */
    void accept(TValue value);
}
