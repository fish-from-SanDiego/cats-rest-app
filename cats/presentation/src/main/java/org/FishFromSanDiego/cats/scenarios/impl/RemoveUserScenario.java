package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.UserManagementService;

import java.util.Optional;

public class RemoveUserScenario extends ScenarioBase {
    private final UserManagementService userManagementService;

    public RemoveUserScenario(
            Scenario previousScenario, DaoContext daoContext, UserManagementService userManagementService) {
        super(previousScenario, "Remove user", "Removing user", daoContext);
        this.userManagementService = userManagementService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<Long> userId = Optional.empty();
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            userId = Input.AskLong("Enter user id: ");
        } while (userId.isEmpty());
        try {
            userManagementService.removeUser(userId.get());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("User with id %d removed successfully%n", userId.get());
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
