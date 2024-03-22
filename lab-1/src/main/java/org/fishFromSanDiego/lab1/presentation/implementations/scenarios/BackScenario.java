package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;

import java.util.Optional;

public class BackScenario implements Scenario {
    public BackScenario(Scenario previousScenario) {
        this.previousScenario = previousScenario;
    }

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
