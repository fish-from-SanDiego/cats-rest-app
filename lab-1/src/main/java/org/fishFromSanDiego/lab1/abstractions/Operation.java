package org.fishFromSanDiego.lab1.abstractions;

import java.util.Optional;

public interface Operation<TResult> {
    TResult execute();

    Optional<Operation<TResult>> getRevertedOperation();
}
