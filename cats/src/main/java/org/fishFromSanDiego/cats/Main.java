package org.fishFromSanDiego.cats;


import jakarta.persistence.Persistence;
import org.FishFromSanDiego.cats.dao.CatDaoImpl;
import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dao.UserDaoImpl;
import org.FishFromSanDiego.cats.scenarios.impl.*;
import org.FishFromSanDiego.cats.services.UserManagementServiceImpl;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("cats-db");
        var daoContext = new DaoContext(new CatDaoImpl(emf), new UserDaoImpl(emf));
        ChoiceScenario initialScenario = new ChoiceScenario(null,
                "",
                "Choose login option",
                daoContext,
                new ArrayList<>(),
                true);
        initialScenario.AddScenario(new UserManagementScenario(initialScenario,
                        daoContext,
                        true,
                        new UserManagementServiceImpl(daoContext)))
                .AddScenario(new EnterAsUserScenario(initialScenario, daoContext))
                .AddScenario(new QuitScenario());
        var runner = new ScenarioRunnerImpl(initialScenario);
        runner.RunScenarios();
    }
}

