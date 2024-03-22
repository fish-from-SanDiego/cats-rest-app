package org.fishFromSanDiego.lab1.presentation.abstractions.scenarios;

import java.util.Optional;

public interface Scenario {
    String getName();

    String getTitle();

    Optional<Scenario> execute();

    Optional<Scenario> getPreviousScenario();

    boolean isRepeatable();
}
