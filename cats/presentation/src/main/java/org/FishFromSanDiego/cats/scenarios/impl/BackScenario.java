package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.scenarios.Scenario;

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
