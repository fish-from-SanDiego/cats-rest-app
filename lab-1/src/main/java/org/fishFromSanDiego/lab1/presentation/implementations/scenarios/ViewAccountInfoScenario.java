package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Account;
import org.fishFromSanDiego.lab1.models.FetchedModel;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.AccountService;

import java.util.Optional;

public class ViewAccountInfoScenario extends ScenarioBase {
    private final AccountService accountService;

    public ViewAccountInfoScenario(Scenario previousScenario,
                                   RepositoryContext repositoryContext,
                                   AccountService accountService) {
        super(previousScenario,
                "View account info",
                "Account info\nId\tType\tBalance",
                repositoryContext);
        this.accountService = accountService;
    }

    @Override
    public Optional<Scenario> execute() {
        FetchedModel<Account> account;
        try {
            account = accountService.getAccount();
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf(
                "%d\t%s\t%s%n",
                account.id(),
                account.value().accountType().getName(),
                account.value().balance().toString());
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
