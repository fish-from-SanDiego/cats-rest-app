package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.CentralBankService;
import org.fishFromSanDiego.lab1.services.implementations.CentralBankLoginService;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CentralBankLoginScenario extends ScenarioBase {
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
        } while (passwordOptional.isEmpty() || bsOptional.isEmpty());
        return Optional.of(
                new ChoiceScenario(this, "", "Choose option", repositoryContext,
                        Stream.of(
                                new RegisterBankScenario(this, repositoryContext, bsOptional.get()),
                                new NotifyAboutCommissionScenario(this, repositoryContext, bsOptional.get()),
                                new NotifyAboutPercentChargeScenario(this, repositoryContext, bsOptional.get()),
                                new NotifyAboutPercentPaymentScenario(this, repositoryContext, bsOptional.get()),
                                new BackScenario(this)
                        ).collect(Collectors.toList()),
                        true));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
