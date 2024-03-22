package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;

import java.util.Optional;

public class QuitScenario implements Scenario {
    @Override
    public String getName() {
        return "Quit";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public Optional<Scenario> execute() {
        return Optional.empty();
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
