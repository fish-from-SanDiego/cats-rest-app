package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * The type Choice scenario.
 */
public class ChoiceScenario extends ScenarioBase {

    /**
     * The Scenarios.
     */
    List<Scenario> scenarios;
    /**
     * The Repeatable.
     */
    boolean repeatable;

    /**
     * Instantiates a new Choice scenario.
     *
     * @param previousScenario  the previous scenario
     * @param name              the name
     * @param title             the title
     * @param repositoryContext the repository context
     * @param scenarios         the scenarios
     * @param isRepeatable      the is repeatable
     */
    public ChoiceScenario(Scenario previousScenario,
                          String name, String title,
                          RepositoryContext repositoryContext,
                          Collection<Scenario> scenarios,
                          boolean isRepeatable) {
        super(previousScenario, name, title, repositoryContext);
        this.scenarios = new ArrayList<>(scenarios.stream().toList());
        this.repeatable = isRepeatable;
    }

    /**
     * Add scenario choice scenario.
     *
     * @param scenario the scenario
     * @return the choice scenario
     */
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
            IntStream.range(0, scenarios.size()).forEach(i -> System.out.printf(template, i, scenarios.get(i).getName()));
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
