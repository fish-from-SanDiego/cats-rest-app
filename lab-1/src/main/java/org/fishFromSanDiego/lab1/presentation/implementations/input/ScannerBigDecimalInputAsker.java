package org.fishFromSanDiego.lab1.presentation.implementations.input;

import org.fishFromSanDiego.lab1.presentation.abstractions.input.InputAsker;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

public class ScannerBigDecimalInputAsker implements InputAsker<BigDecimal> {
    private final Scanner scanner;

    public ScannerBigDecimalInputAsker(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Optional<BigDecimal> Ask(String prompt) {
        System.out.print(prompt);
        try {
            return Optional.of(scanner.nextBigDecimal());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
