package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.UserService;

import java.util.List;
import java.util.Optional;

public class ViewAllCatsScenario extends ScenarioBase {
    private final UserService userService;

    public ViewAllCatsScenario(
            Scenario previousScenario, DaoContext daoContext, UserService userService) {
        super(previousScenario, "View my cats", "Cats", daoContext);
        this.userService = userService;
    }

    @Override
    public Optional<Scenario> execute() {
        List<CatDto> cats;
        try {
            cats = userService.getAllCatInfos();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.flush();
        System.out.println(this.getTitle());
        cats.forEach(cat -> System.out.println(cat.toString()));
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
