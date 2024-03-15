package org.fishFromSanDiego.lab1.abstractions;

public interface Observer<TValue> {
    void accept(TValue value);
}
