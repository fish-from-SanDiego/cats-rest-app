package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.scenarios.Scenario;

import java.util.Optional;

public abstract class ScenarioBase implements Scenario {
    private final String name;
    private final String title;
    protected final DaoContext daoContext;
    protected final Scenario previousScenario;

    public ScenarioBase(Scenario previousScenario, String name, String title, DaoContext daoContext) {
        this.name = name;
        this.title = title;
        this.previousScenario = previousScenario;
        this.daoContext = daoContext;
    }

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
        return previousScenario == null ? Optional.empty() : Optional.of(previousScenario);
    }

    @Override
    public abstract Optional<Scenario> execute();

    @Override
    public abstract boolean isRepeatable();
}
