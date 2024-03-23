package org.fishFromSanDiego.lab1.presentation.implementations.scenarios;

import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.presentation.abstractions.scenarios.Scenario;
import org.fishFromSanDiego.lab1.presentation.implementations.input.Input;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;
import org.fishFromSanDiego.lab1.services.implementations.BankLoginService;

import java.util.ArrayList;
import java.util.Optional;

public class BankLoginScenario extends ScenarioBase {
    public BankLoginScenario(Scenario previousScenario,
                             RepositoryContext repositoryContext) {
        super(previousScenario, "Login as bank", "Bank login page", repositoryContext);
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<String> passwordOptional = Optional.empty();
        Optional<Integer> idOptional = Optional.empty();
        Optional<BankService> bsOptional = Optional.empty();
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            idOptional = Input.AskInt("Enter bank id: ");
            if (idOptional.isEmpty())
                continue;
            passwordOptional = Input.AskString("Enter bank password: ");
            if (passwordOptional.isPresent())
                bsOptional = new BankLoginService(repositoryContext, idOptional.get()).tryLogin(passwordOptional.get());
        } while (passwordOptional.isEmpty());
        if (bsOptional.isEmpty())
            return Optional.of(new BackScenario(this));
        ChoiceScenario result = new ChoiceScenario(this, "", "Choose option", repositoryContext,
                new ArrayList<>(),
                true);
        result
                .AddScenario(new ViewBankInfoScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new RegisterBankClientScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new SetDepositCardPercentScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new SetDebitCardPercentScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new SetCreditCardLimitScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new SetCreditCardCommissionScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new SetSuspiciousDepositSumScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new SetSuspiciousWithdrawalSumScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new ViewAllTransactionsScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new RevertTransactionScenario(result, repositoryContext, bsOptional.get()))
                .AddScenario(new BackScenario(this));
        return Optional.of(result);
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
