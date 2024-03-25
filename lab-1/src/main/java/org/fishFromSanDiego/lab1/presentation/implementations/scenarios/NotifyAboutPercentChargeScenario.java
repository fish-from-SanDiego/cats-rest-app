package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.CentralBankService;

import java.util.Optional;

/**
 * The type Notify about percent charge scenario.
 */
public class NotifyAboutPercentChargeScenario extends ScenarioBase {
    private final CentralBankService centralBankService;

    /**
     * Instantiates a new Notify about percent charge scenario.
     *
     * @param previousScenario   the previous scenario
     * @param repositoryContext  the repository context
     * @param centralBankService the central bank service
     */
    public NotifyAboutPercentChargeScenario(
            Scenario previousScenario,
            RepositoryContext repositoryContext,
            CentralBankService centralBankService) {
        super(previousScenario, "Notify banks about percent charge", "Percent charge notification", repositoryContext);
        this.centralBankService = centralBankService;
    }

    @Override
    public Optional<Scenario> execute() {
        try {
            centralBankService.notifyBanksAboutPercentCharge();
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.println("Percents charged successfully");
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
