package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.UserService;

import java.util.Optional;

public class RemoveCatScenario extends ScenarioBase {
    private final UserService userService;

    public RemoveCatScenario(
            Scenario previousScenario, DaoContext daoContext, UserService userService) {
        super(previousScenario, "Remove cat", "Removing cat", daoContext);
        this.userService = userService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<Long> catId = Optional.empty();
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            catId = Input.AskLong("Enter cat id: ");
        } while (catId.isEmpty());
        try {
            userService.removeCat(catId.get());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("Cat with id %d removed successfully%n", catId.get());
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
