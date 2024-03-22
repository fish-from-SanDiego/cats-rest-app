package org.fishFromSanDiego.lab1.presentation.abstractions.input;

public interface InputAsker<T> {
    T Ask(String prompt);
}
