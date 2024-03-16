package org.fishFromSanDiego.lab1.abstractions;

import java.util.Optional;

public interface Operation<TResult> {
    TResult execute();

    boolean canBeReverted();

    Optional<Operation<TResult>> getRevertedOperation();
}
