package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.CatService;

import java.util.Optional;

public class FriendCatScenario extends ScenarioBase {
    private final CatService catService;

    public FriendCatScenario(
            Scenario previousScenario, DaoContext daoContext, CatService catService) {
        super(previousScenario, "Friend cat", "Friend invite", daoContext);
        this.catService = catService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<Long> friendCatId = Optional.empty();
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            friendCatId = Input.AskLong("Enter friend id: ");
        } while (friendCatId.isEmpty());
        try {
            catService.friendCat(friendCatId.get());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("Cat with id %d friended successfully%n", friendCatId.get());
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
