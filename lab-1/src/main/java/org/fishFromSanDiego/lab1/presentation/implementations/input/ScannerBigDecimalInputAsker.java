package org.fishFromSanDiego.lab1.presentation.implementations.input;

import org.fishFromSanDiego.lab1.presentation.abstractions.input.InputAsker;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

/**
 * The type Scanner big decimal input asker.
 */
public class ScannerBigDecimalInputAsker implements InputAsker<BigDecimal> {
    private final Scanner scanner;

    /**
     * Instantiates a new Scanner big decimal input asker.
     *
     * @param scanner the scanner
     */
    public ScannerBigDecimalInputAsker(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Optional<BigDecimal> Ask(String prompt) {
        System.out.print(prompt);
        try {
            var bigDecimal = scanner.nextBigDecimal();
            scanner.nextLine();
            return Optional.of(bigDecimal);
        } catch (Exception e) {
            scanner.nextLine();
            return Optional.empty();
        }
    }
}
