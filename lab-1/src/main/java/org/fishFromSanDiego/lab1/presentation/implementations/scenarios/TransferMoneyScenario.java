package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.AccountService;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * The type Transfer money scenario.
 */
public class TransferMoneyScenario extends ScenarioBase {
    private final AccountService accountService;

    /**
     * Instantiates a new Transfer money scenario.
     *
     * @param previousScenario  the previous scenario
     * @param repositoryContext the repository context
     * @param accountService    the account service
     */
    public TransferMoneyScenario(Scenario previousScenario,
                                 RepositoryContext repositoryContext,
                                 AccountService accountService) {
        super(previousScenario,
                "Transfer money",
                "Transfer money",
                repositoryContext);
        this.accountService = accountService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<BigDecimal> amount = Optional.empty();
        Optional<Integer> recipientBankId = Optional.empty();
        Optional<Integer> recipientAccountId = Optional.empty();
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            amount = Input.AskBigDecimal("Enter money amount to transfer: ");
            if (amount.isEmpty())
                continue;
            recipientBankId = Input.AskInt("Enter recipient bank id: ");
            if (recipientBankId.isEmpty())
                continue;
            recipientAccountId = Input.AskInt("Enter recipient account id: ");
        } while (amount.isEmpty() || recipientBankId.isEmpty() || recipientAccountId.isEmpty());
        try {
            accountService.transferMoney(amount.get(), recipientBankId.get(), recipientAccountId.get());
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.println("Money transferred was successful");
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
