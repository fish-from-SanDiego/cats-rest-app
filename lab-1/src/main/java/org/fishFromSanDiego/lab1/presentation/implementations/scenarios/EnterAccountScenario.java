package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.AccountService;
import org.fishFromSanDiego.lab1.services.abstractions.ClientService;

import java.util.ArrayList;
import java.util.Optional;

public class EnterAccountScenario extends ScenarioBase {
    private final ClientService clientService;

    public EnterAccountScenario(Scenario previousScenario,
                                RepositoryContext repositoryContext,
                                ClientService clientService) {
        super(previousScenario,
                "Enter account",
                "Entering account",
                repositoryContext);
        this.clientService = clientService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<Integer> accountId = Optional.empty();
        Optional<AccountService> accountService = Optional.empty();
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            accountId = Input.AskInt("Enter account id: ");
            if (accountId.isPresent())
                accountService = clientService.findAccountServiceById(accountId.get());
        } while (accountId.isEmpty());
        if (accountService.isEmpty())
            return Optional.of(new BackScenario(this));

        ChoiceScenario result = new ChoiceScenario(this, "", "Choose option", repositoryContext,
                new ArrayList<>(),
                true);
        result
                .AddScenario(new ViewAccountInfoScenario(result, repositoryContext, accountService.get()))
                .AddScenario(new DepositMoneyScenario(result, repositoryContext, accountService.get()))
                .AddScenario(new WithdrawMoneyScenario(result, repositoryContext, accountService.get()))
                .AddScenario(new TransferMoneyScenario(result, repositoryContext, accountService.get()))
                .AddScenario(new BackScenario(this));
        return Optional.of(result);
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
