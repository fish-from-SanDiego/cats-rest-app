package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.UserService;

import java.util.Optional;

public class ViewUserInfoScenario extends ScenarioBase {
    private final UserService userService;

    public ViewUserInfoScenario(
            Scenario previousScenario, DaoContext daoContext, UserService userService) {
        super(previousScenario, "View user info", "Info", daoContext);
        this.userService = userService;
    }

    @Override
    public Optional<Scenario> execute() {
        UserDto user;
        try {
            user = userService.getUserInfo();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.flush();
        System.out.println(this.getTitle());
        System.out.println(user.toString());
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
