package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.UserManagementService;

import java.util.List;
import java.util.Optional;

public class ViewAllUsersScenario extends ScenarioBase {
    private final UserManagementService userManagementService;

    public ViewAllUsersScenario(
            Scenario previousScenario, DaoContext daoContext, UserManagementService userManagementService) {
        super(previousScenario, "View all users", "Users", daoContext);
        this.userManagementService = userManagementService;
    }

    @Override
    public Optional<Scenario> execute() {
        List<UserDto> users;
        try {
            users = userManagementService.getAllUserInfos();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.flush();
        System.out.println(this.getTitle());
        users.forEach(user -> System.out.println(user.toString()));
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
