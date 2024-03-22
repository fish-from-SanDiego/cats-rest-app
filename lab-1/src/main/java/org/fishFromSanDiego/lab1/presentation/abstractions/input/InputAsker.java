package org.fishFromSanDiego.lab1.presentation.abstractions.input;

import java.util.Optional;

public interface InputAsker<T> {
    Optional<T> Ask(String prompt);
}
