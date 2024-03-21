import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Bank;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.repositories.implementations.NonPersistentAccountRepository;
import org.fishFromSanDiego.lab1.repositories.implementations.NonPersistentBankRepository;
import org.fishFromSanDiego.lab1.repositories.implementations.NonPersistentClientRepository;
import org.fishFromSanDiego.lab1.services.abstractions.BankService;
import org.fishFromSanDiego.lab1.services.implementations.BankLoginService;
import org.fishFromSanDiego.lab1.services.implementations.ConcreteBankService;
import org.fishFromSanDiego.lab1.services.implementations.ConcreteCentralBankService;
import org.fishFromSanDiego.lab1.services.implementations.DepositChargeStrategyConstImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

public class ServiceTests {
    private RepositoryContext repoContext;

    @BeforeEach
    public void init() {
        repoContext = new RepositoryContext(
                new NonPersistentAccountRepository(),
                new NonPersistentBankRepository("12345"),
                new NonPersistentClientRepository()
        );
    }

    @Test
    public void testBS() throws ServiceException {
        // Arrange
        var cbs = new ConcreteCentralBankService(repoContext);
        cbs.registerNewBank(
                Bank.builder()
                        .suspiciousClientDepositLimit(BigDecimal.valueOf(1000))
                        .creditCardCommission(BigDecimal.valueOf(200))
                        .debitCardPercent(BigDecimal.valueOf(0.01))
                        .suspiciousClientWithdrawalLimit(BigDecimal.valueOf(100))
                        .depositChargeStrategy(new DepositChargeStrategyConstImpl(BigDecimal.valueOf(100)))
                        .name("my bank")
                        .creditCardLimit(BigDecimal.valueOf(100))
                        .build(), "12345");
        var bsLogin = new BankLoginService(repoContext, 0);

        // Act
        var logged = bsLogin.tryLogin("12345");
        var notLogged = bsLogin.tryLogin("45ey ");


        // Assert
        Assertions.assertEquals(Optional.empty(), notLogged);
        Assertions.assertFalse(logged.isEmpty());
        Assertions.assertEquals(0, logged.get().getBank().id());
        Assertions.assertEquals("my bank", logged.get().getBank().value().name());

    }
}
