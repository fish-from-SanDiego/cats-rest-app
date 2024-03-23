package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;

import java.util.Optional;

/**
 * The type Back scenario.
 */
public class BackScenario implements Scenario {
    /**
     * Instantiates a new Back scenario.
     *
     * @param previousScenario the previous scenario
     */
    public BackScenario(Scenario previousScenario) {
        this.previousScenario = previousScenario;
    }

    /**
     * The Previous scenario.
     */
    Scenario previousScenario;

    @Override
    public String getName() {
        return "Back";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public Optional<Scenario> execute() {
        while (!previousScenario.isRepeatable()) {
            var optional = previousScenario.getPreviousScenario();
            if (optional.isEmpty())
                return optional;
            previousScenario = optional.get();
        }
        return Optional.of(previousScenario);
    }

    @Override
    public Optional<Scenario> getPreviousScenario() {
        return Optional.empty();
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
