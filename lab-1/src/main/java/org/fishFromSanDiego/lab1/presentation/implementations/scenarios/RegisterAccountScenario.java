package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.AccountType;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.ClientService;

import java.util.Optional;

/**
 * The type Register account scenario.
 */
public class RegisterAccountScenario extends ScenarioBase {
    private final ClientService clientService;

    /**
     * Instantiates a new Register account scenario.
     *
     * @param previousScenario  the previous scenario
     * @param repositoryContext the repository context
     * @param clientService     the client service
     */
    public RegisterAccountScenario(Scenario previousScenario,
                                   RepositoryContext repositoryContext,
                                   ClientService clientService) {
        super(previousScenario,
                "Register new account",
                "Account Registration",
                repositoryContext);
        this.clientService = clientService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<String> accountTypeEntered = Optional.empty();
        String lowerCase = null;
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            accountTypeEntered = Input.AskString("Enter account type (Debit/Deposit/Credit): ");
            if (accountTypeEntered.isPresent())
                lowerCase = accountTypeEntered.get().toLowerCase();
        } while (accountTypeEntered.isEmpty() ||
                (!lowerCase.equals("debit") && !lowerCase.equals("deposit") && !lowerCase.equals("credit")));
        int accountId;
        try {
            if (lowerCase.equals("debit"))
                accountId = clientService.registerNewAccount(new AccountType.Debit());
            else if (lowerCase.equals("deposit"))
                accountId = clientService.registerNewAccount(new AccountType.Deposit());
            else
                accountId = clientService.registerNewAccount(new AccountType.Credit());
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("Account with id %d registered successfully%n", accountId);
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));

    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
