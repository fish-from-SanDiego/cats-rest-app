package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.CatService;

import java.util.Optional;

public class ViewCatInfoScenario extends ScenarioBase {
    private final CatService catService;

    public ViewCatInfoScenario(
            Scenario previousScenario, DaoContext daoContext, CatService catService) {
        super(previousScenario, "View cat info", "Info", daoContext);
        this.catService = catService;
    }

    @Override
    public Optional<Scenario> execute() {
        CatDto cat;
        try {
            cat = catService.getCatInfo();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.flush();
        System.out.println(this.getTitle());
        System.out.println(cat.toString());
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
