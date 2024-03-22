package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.services.abstractions.CentralBankService;

import java.util.Optional;

public class RegisterBankScenario extends ScenarioBase {
    private final CentralBankService centralBankService;

    public RegisterBankScenario(Scenario previousScenario,
                                RepositoryContext repositoryContext,
                                CentralBankService centralBankService) {
        super(previousScenario, "Register new bank", "Bank registration", repositoryContext);
        this.centralBankService = centralBankService;
    }

    @Override
    public Optional<Scenario> execute() {
        return Optional.empty();
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
