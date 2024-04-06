package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.scenarios.Scenario;

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
