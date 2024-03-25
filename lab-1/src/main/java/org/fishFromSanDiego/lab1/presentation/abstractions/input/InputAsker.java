package org.fishFromSanDiego.lab1.presentation.abstractions.input;

import java.util.Optional;

/**
 * The interface for asking for input.
 *
 * @param <T> the type parameter
 */
public interface InputAsker<T> {
    /**
     * Ask optional.
     *
     * @param prompt the prompt which should be printed before input
     * @return the optional (it's present if no errors occurred)
     */
    Optional<T> Ask(String prompt);
}
