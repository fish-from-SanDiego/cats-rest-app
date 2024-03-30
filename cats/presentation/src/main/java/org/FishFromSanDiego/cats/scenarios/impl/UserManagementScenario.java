package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.UserManagementService;

import java.util.ArrayList;

public class UserManagementScenario extends ChoiceScenario {
    private final UserManagementService userManagementService;

    public UserManagementScenario(
            Scenario previousScenario,
            DaoContext daoContext,
            boolean isRepeatable,
            UserManagementService userManagementService) {
        super(previousScenario, "Go to user management", "Managing users", daoContext, new ArrayList<>(), isRepeatable);
        this.userManagementService = userManagementService;
        super.AddScenario(new RegisterUserScenario(this, daoContext, userManagementService))
                .AddScenario(new ViewAllUsersScenario(this, daoContext, userManagementService))
                .AddScenario(new BackScenario(previousScenario));
    }
}
