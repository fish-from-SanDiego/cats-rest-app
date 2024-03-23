package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.CentralBankService;
import org.fishFromSanDiego.lab1.services.implementations.CentralBankLoginService;

import java.util.ArrayList;
import java.util.Optional;

/**
 * The type Central bank login scenario.
 */
public class CentralBankLoginScenario extends ScenarioBase {
    /**
     * Instantiates a new Central bank login scenario.
     *
     * @param previousScenario  the previous scenario
     * @param repositoryContext the repository context
     */
    public CentralBankLoginScenario(Scenario previousScenario,
                                    RepositoryContext repositoryContext) {
        super(previousScenario, "Login as central bank", "Central bank login page", repositoryContext);
    }

    @Override
    public Optional<Scenario> execute() {
        var loginService = new CentralBankLoginService(repositoryContext);
        Optional<String> passwordOptional = Optional.empty();
        Optional<CentralBankService> bsOptional = Optional.empty();
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            passwordOptional = Input.AskString("Enter central bank password: ");
            if (passwordOptional.isPresent())
                bsOptional = loginService.tryLogin(passwordOptional.get());
        } while (passwordOptional.isEmpty());
        if (bsOptional.isEmpty())
            return Optional.of(new BackScenario(this));

        ChoiceScenario result = new ChoiceScenario(this, "", "Choose option", repositoryContext,
                new ArrayList<>(),
                true);
        result
                .AddScenario(new RegisterBankScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new NotifyAboutCommissionScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new NotifyAboutPercentChargeScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new NotifyAboutPercentPaymentScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new BackScenario(this));
        return Optional.of(result);
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
