package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.UserManagementService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class RegisterUserScenario extends ScenarioBase {
    private final UserManagementService userManagementService;

    public RegisterUserScenario(
            Scenario previousScenario, DaoContext daoContext, UserManagementService userManagementService) {
        super(previousScenario, "Register new user", "User registration", daoContext);
        this.userManagementService = userManagementService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<String> firstName = Optional.empty();
        Optional<String> secondName = Optional.empty();
        Optional<LocalDate> birthDate = Optional.empty();
        Optional<String> birthDateStr = Optional.empty();
        String pattern = "dd-MM-yyyy";
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            firstName = Input.AskString("Enter user first name: ");
            if (firstName.isEmpty())
                continue;
            secondName = Input.AskString("Enter user second name: ");
            if (secondName.isEmpty())
                continue;
            birthDateStr = Input.AskString("Enter user birth date (in format dd-MM-yyyy): ");
            if (birthDateStr.isEmpty())
                continue;
            try {
                birthDate = Optional.of(LocalDate.parse(birthDateStr.get(), DateTimeFormatter.ofPattern(pattern)));
            } catch (Exception e) {
                birthDate = Optional.empty();
                continue;
            }
        } while (firstName.isEmpty() || firstName.get().isEmpty() || secondName.isEmpty() || secondName.get()
                .isEmpty() || birthDateStr.isEmpty() || birthDate.isEmpty());
        var user = UserDto.builder()
                .firstName(firstName.get())
                .secondName(secondName.get())
                .birthDate(birthDate.get())
                .build();
        long userId;
        try {
            userId = userManagementService.registerNewUser(user).getId();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("User with id %d registered successfully%n", userId);
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
