package org.fishFromSanDiego.lab1.presentation.abstractions.scenarios;

import java.util.Optional;

/**
 * The interface Scenario.
 */
public interface Scenario {
    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets title.
     *
     * @return the title
     */
    String getTitle();

    /**
     * Execute optional.
     *
     * @return the optional
     */
    Optional<Scenario> execute();

    /**
     * Gets previous scenario.
     *
     * @return the previous scenario
     */
    Optional<Scenario> getPreviousScenario();

    /**
     * Is repeatable boolean.
     *
     * @return the boolean
     */
    boolean isRepeatable();
}
