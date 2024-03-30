package org.FishFromSanDiego.cats.scenarios.impl;


import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.scenarios.Scenario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ChoiceScenario extends ScenarioBase {

    List<Scenario> scenarios;
    boolean repeatable;

    public ChoiceScenario(Scenario previousScenario,
                          String name,
                          String title,
                          DaoContext daoContext,
                          Collection<Scenario> scenarios,
                          boolean isRepeatable) {
        super(previousScenario, name, title, daoContext);
        this.scenarios = new ArrayList<>(scenarios.stream().toList());
        this.repeatable = isRepeatable;
    }

    public ChoiceScenario AddScenario(Scenario scenario) {
        scenarios.add(scenario);
        return this;
    }

    @Override
    public Optional<Scenario> execute() {
        String template = "%d. %s%n";
        Optional<Integer> optional = Optional.empty();
        while (optional.isEmpty() || optional.get() < 0 || optional.get() >= scenarios.size()) {
            System.out.flush();
            System.out.println(this.getTitle());
            IntStream.range(0, scenarios.size())
                    .forEach(i -> System.out.printf(template, i, scenarios.get(i).getName()));
            optional = Input.AskInt("Enter option number: ");
        }
        Input.WaitTillEnterPress();
        return Optional.of(scenarios.get(optional.get()));
    }

    @Override
    public boolean isRepeatable() {
        return this.repeatable;
    }
}
