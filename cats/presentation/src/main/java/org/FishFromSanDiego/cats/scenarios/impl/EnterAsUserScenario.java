package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.UserServiceImpl;

import java.util.ArrayList;
import java.util.Optional;

public class EnterAsUserScenario extends ScenarioBase {
    public EnterAsUserScenario(
            Scenario previousScenario, DaoContext daoContext) {
        super(previousScenario, "Enter as user", "Entering", daoContext);
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<Long> userId = Optional.empty();
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            userId = Input.AskLong("Enter user id: ");
        } while (userId.isEmpty());
        var service = new UserServiceImpl(daoContext, userId.get());
        try {
            service.getUserInfo();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }

        ChoiceScenario result = new ChoiceScenario(this, "", "Choose option", daoContext, new ArrayList<>(), true);
        result.AddScenario(new ViewUserInfoScenario(result, daoContext, service))
                .AddScenario(new ViewUserInfoScenario(result, daoContext, service))
                .AddScenario(new ViewAllCatsScenario(result, daoContext, service))
                .AddScenario(new EnterToCatProfileScenario(result, daoContext, service))
                .AddScenario(new RegisterCatScenario(result, daoContext, service))
                .AddScenario(new RemoveCatScenario(result, daoContext, service))
                .AddScenario(new BackScenario(this));
        return Optional.of(result);
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
