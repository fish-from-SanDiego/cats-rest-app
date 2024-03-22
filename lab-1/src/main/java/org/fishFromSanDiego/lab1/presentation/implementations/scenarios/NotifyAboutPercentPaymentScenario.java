package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.CentralBankService;

import java.util.Optional;

public class NotifyAboutPercentPaymentScenario extends ScenarioBase {
    private final CentralBankService centralBankService;

    public NotifyAboutPercentPaymentScenario(
            Scenario previousScenario,
            RepositoryContext repositoryContext,
            CentralBankService centralBankService) {
        super(previousScenario, "Notify banks about percent payment", "Percent payment notification", repositoryContext);
        this.centralBankService = centralBankService;
    }

    @Override
    public Optional<Scenario> execute() {
        try {
            centralBankService.notifyBanksAboutPercentPayment();
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.println("Percents paid successfully");
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
