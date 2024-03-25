package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.FetchedModel;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.models.Transaction;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;

import java.util.Collection;
import java.util.Optional;

/**
 * The type View all transactions scenario.
 */
public class ViewAllTransactionsScenario extends ScenarioBase {
    private final BankService bankService;

    /**
     * Instantiates a new View all transactions scenario.
     *
     * @param previousScenario  the previous scenario
     * @param repositoryContext the repository context
     * @param bankService       the bank service
     */
    public ViewAllTransactionsScenario(Scenario previousScenario,
                                       RepositoryContext repositoryContext,
                                       BankService bankService) {
        super(previousScenario,
                "View all bank transactions",
                "Bank transactions\nId\tType\tSum",
                repositoryContext);
        this.bankService = bankService;
    }

    @Override
    public Optional<Scenario> execute() {
        Collection<FetchedModel<Transaction>> transactions;
        try {
            transactions = bankService.getAllTransactions();
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.flush();
        System.out.println(this.getTitle());
        transactions.forEach(
                transaction ->
                        System.out.printf(
                                "%d\t%s\t%s%n",
                                transaction.id(),
                                transaction.value().getTransactionTypeName(),
                                transaction.value().getSum()));
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
