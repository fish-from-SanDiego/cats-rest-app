package org.FishFromSanDiego.cats.scenarios.impl;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.input.Input;
import org.FishFromSanDiego.cats.models.Colour;
import org.FishFromSanDiego.cats.scenarios.Scenario;
import org.FishFromSanDiego.cats.services.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.IllegalFormatException;
import java.util.Optional;

public class RegisterCatScenario extends ScenarioBase {
    private final UserService userService;

    public RegisterCatScenario(
            Scenario previousScenario, DaoContext daoContext, UserService userService) {
        super(previousScenario, "Register new cat", "Cat registration", daoContext);
        this.userService = userService;
    }

    @Override
    public Optional<Scenario> execute() {
        Optional<String> name = Optional.empty();
        Optional<LocalDate> birthDate = Optional.empty();
        Optional<String> birthDateStr = Optional.empty();
        Optional<String> breed = Optional.empty();
        Optional<String> colourStr = Optional.empty();
        Optional<Colour> colour = Optional.empty();
        String pattern = "dd-MM-yyyy";
        do {
            System.out.flush();
            System.out.println(this.getTitle());
            name = Input.AskString("Enter cat name: ");
            if (name.isEmpty())
                continue;
            breed = Input.AskString("Enter cat's breed: ");
            if (breed.isEmpty())
                continue;
            birthDateStr = Input.AskString("Enter cat birth date (in format dd-MM-yyyy): ");
            if (birthDateStr.isEmpty())
                continue;
            try {
                birthDate = Optional.of(LocalDate.parse(birthDateStr.get(), DateTimeFormatter.ofPattern(pattern)));
            } catch (Exception e) {
                birthDate = Optional.empty();
                continue;
            }
            colourStr = Input.AskString("Enter cat's colour: ");
            if (colourStr.isEmpty())
                continue;
            try {
                colour = Optional.of(Colour.valueOf(colourStr.get().toUpperCase()));
            } catch (IllegalFormatException e) {
                colour = Optional.empty();
            }
        } while (name.isEmpty() || name.get().isEmpty() || breed.isEmpty() || breed.get()
                .isEmpty() || birthDateStr.isEmpty() || birthDate.isEmpty() || colourStr.isEmpty() || colour.isEmpty());
        var cat = CatDto.builder()
                .name(name.get())
                .breed(breed.get())
                .birthDate(birthDate.get())
                .colour(colour.get())
                .build();
        long catId;
        try {
            catId = userService.registerNewCat(cat).getId();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            Input.WaitTillEnterPress();
            return Optional.of(new BackScenario(this));
        }
        System.out.printf("Cat with id %d registered successfully%n", catId);
        Input.WaitTillEnterPress();
        return Optional.of(new BackScenario(this));
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }
}
