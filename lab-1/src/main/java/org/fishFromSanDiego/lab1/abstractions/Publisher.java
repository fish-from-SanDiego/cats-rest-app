package org.fishFromSanDiego.lab1.abstractions;

public interface Publisher<TValue> {
    void notifySubscribers(TValue value);

    void subscribe(Observer<TValue> subscriber);
}
