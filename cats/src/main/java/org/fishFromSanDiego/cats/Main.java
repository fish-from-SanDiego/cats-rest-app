package org.fishFromSanDiego.cats;


import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dao.PostgresCatDao;
import org.FishFromSanDiego.cats.dao.PostgresUserDao;
import org.FishFromSanDiego.cats.scenarios.impl.*;
import org.FishFromSanDiego.cats.services.UserManagementServiceImpl;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        var daoContext = new DaoContext(new PostgresCatDao(), new PostgresUserDao());
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

