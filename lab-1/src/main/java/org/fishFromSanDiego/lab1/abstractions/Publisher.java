package org.fishFromSanDiego.lab1.abstractions;

/**
 * The interface Publisher.
 *
 * @param <TValue> the type parameter
 */
public interface Publisher<TValue> {
    /**
     * Notify subscribers.
     *
     * @param value the value
     */
    void notifySubscribers(TValue value);

    /**
     * Subscribe new subscriber.
     *
     * @param subscriber the subscriber
     */
    void subscribe(Observer<TValue> subscriber);
}
