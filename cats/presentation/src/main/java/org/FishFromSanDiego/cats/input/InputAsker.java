package org.FishFromSanDiego.cats.input;

import java.util.Optional;

public interface InputAsker<T> {
    Optional<T> Ask(String prompt);
}
