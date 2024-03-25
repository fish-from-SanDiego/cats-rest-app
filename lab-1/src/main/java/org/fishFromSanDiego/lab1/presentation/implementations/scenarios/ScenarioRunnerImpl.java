package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.ScenarioRunner;

import java.util.Optional;

/**
 * The type Scenario runner.
 */
public class ScenarioRunnerImpl implements ScenarioRunner {
    private final Scenario initialScenario;

    /**
     * Instantiates a new Scenario runner.
     *
     * @param initialScenario the initial scenario
     */
    public ScenarioRunnerImpl(Scenario initialScenario) {
        this.initialScenario = initialScenario;
    }

    @Override
    public void RunScenarios() {
        Optional<Scenario> currentScenario = Optional.of(initialScenario);
        do {
            currentScenario = currentScenario.flatMap(Scenario::execute);
        } while (currentScenario.isPresent());
    }
}
