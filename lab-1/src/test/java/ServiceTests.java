import org.fishFromSanDiego.lab1.exceptions.ServiceException;
import org.fishFromSanDiego.lab1.models.Bank;
import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.RepositoryContext;
import org.fishFromSanDiego.lab1.repositories.implementations.NonPersistentAccountRepository;
import org.fishFromSanDiego.lab1.repositories.implementations.NonPersistentBankRepository;
import org.fishFromSanDiego.lab1.repositories.implementations.NonPersistentClientRepository;
import org.fishFromSanDiego.lab1.services.abstractions.CentralBankService;
import org.fishFromSanDiego.lab1.services.abstractions.ClientService;
import org.fishFromSanDiego.lab1.services.implementations.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

public class ServiceTests {
    private static RepositoryContext repoContext;
    private static CentralBankService centralBS;

    @BeforeEach
    public void init() {
        repoContext = new RepositoryContext(
                new NonPersistentAccountRepository(),
                new NonPersistentBankRepository("12345"),
                new NonPersistentClientRepository()
        );
        centralBS = new ConcreteCentralBankService(repoContext);
    }

    @Test
    public void testBankService() throws ServiceException {
        // Arrange
        registerDefaultBS("my bank", "12345");
        registerDefaultBS("ssss", "aaaaaa");
        var bsLogin = new BankLoginService(repoContext, 0);

        // Act
        var logged = bsLogin.tryLogin("12345").orElseThrow();
        var notLogged = bsLogin.tryLogin("45ey");


        // Assert
        Assertions.assertEquals(Optional.empty(), notLogged);
        Assertions.assertEquals(0, logged.getBank().id());
        Assertions.assertEquals("my bank", logged.getBank().value().name());
    }

    @Test
    public void testClientService() throws ServiceException {
        registerDefaultBS("bank1", "123");
        registerDefaultBS("bank2", "123");
        var b0 = new BankLoginService(repoContext, 0).tryLogin("123").orElseThrow();
        var b1 = new BankLoginService(repoContext, 1).tryLogin("123").orElseThrow();
        b0.registerNewClient(
                defaultClient("John", "Doe").directBuilder(Client.builder())
                        .withAddress("address")
                        .withPassport(1328).build(),
                "123");
        b1.registerNewClient(defaultClient("Name", "Surname"), "123");
        var c0 = new ClientLoginService(repoContext, 0, 0).tryLogin("123").orElseThrow();
        var c1 = new ClientLoginService(repoContext, 1, 0).tryLogin("123").orElseThrow();

        Assertions.assertTrue(c0.getAllAccounts().isEmpty());
        Assertions.assertTrue(c1.getAllAccounts().isEmpty());
    }

    private static void registerDefaultBS(String name, String password) throws ServiceException {
        centralBS.registerNewBank(
                Bank.builder()
                        .suspiciousClientDepositLimit(BigDecimal.valueOf(1000))
                        .creditCardCommission(BigDecimal.valueOf(200))
                        .debitCardPercent(BigDecimal.valueOf(0.01))
                        .suspiciousClientWithdrawalLimit(BigDecimal.valueOf(100))
                        .depositChargeStrategy(new DepositChargeStrategyConstImpl(BigDecimal.valueOf(0.1)))
                        .name(name)
                        .creditCardLimit(BigDecimal.valueOf(100))
                        .build(), password);
    }

    private static Client defaultClient(String name, String surname) {
        return Client.builder().withFullName(name, surname).build();
    }
}
