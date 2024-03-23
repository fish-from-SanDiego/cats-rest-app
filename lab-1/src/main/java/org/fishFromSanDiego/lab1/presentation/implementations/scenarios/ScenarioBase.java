package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;

import java.util.Optional;

/**
 * The type Scenario base.
 */
public abstract class ScenarioBase implements Scenario {
    private final String name;
    private final String title;

    /**
     * Instantiates a new Scenario base.
     *
     * @param previousScenario  the previous scenario
     * @param name              the name
     * @param title             the title
     * @param repositoryContext the repository context
     */
    public ScenarioBase(Scenario previousScenario, String name, String title, RepositoryContext repositoryContext) {
        this.name = name;
        this.title = title;
        this.previousScenario = previousScenario;
        this.repositoryContext = repositoryContext;
    }

    /**
     * The Previous scenario.
     */
    protected final Scenario previousScenario;
    /**
     * The Repository context.
     */
    protected final RepositoryContext repositoryContext;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Optional<Scenario> getPreviousScenario() {
        return previousScenario == null
                ? Optional.empty()
                : Optional.of(previousScenario);
    }

    @Override
    public abstract Optional<Scenario> execute();

    @Override
    public abstract boolean isRepeatable();
}
