package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.CatService;
import org.FishFromSanDiego.cats.services.CatServiceImpl;
import org.FishFromSanDiego.cats.services.UserService;

import java.util.ArrayList;
import java.util.Optional;

public class EnterToCatProfileScenario extends ScenarioBase {
    private final UserService userService;

    public EnterToCatProfileScenario(
            Scenario previousScenario, DaoContext daoContext, UserService userService) {
        super(previousScenario, "Enter to cat profile", "", daoContext);
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
        CatService service;
        try {
            service = new CatServiceImpl(daoContext, catId.get(), userService.getUserInfo().getId());
            service.getCatInfo();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }

        ChoiceScenario result = new ChoiceScenario(this, "", "Choose option", daoContext, new ArrayList<>(), true);
        result.AddScenario(new ViewCatInfoScenario(result, daoContext, service))
                .AddScenario(new FriendCatScenario(result, daoContext, service))
                .AddScenario(new UnfriendCatScenario(result, daoContext, service))
                .AddScenario(new ViewFriendsScenario(result, daoContext, service))
                .AddScenario(new BackScenario(this));
        return Optional.of(result);
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
