package org.fishFromSanDiego.lab1.presentation.implementations.input;

import org.fishFromSanDiego.lab1.presentation.abstractions.input.InputAsker;

import java.util.Optional;
import java.util.Scanner;

/**
 * The type Scanner string input asker.
 */
public class ScannerStringInputAsker implements InputAsker<String> {
    private final Scanner scanner;

    /**
     * Instantiates a new Scanner string input asker.
     *
     * @param scanner the scanner
     */
    public ScannerStringInputAsker(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Optional<String> Ask(String prompt) {
        System.out.print(prompt);
        try {
            return Optional.of(scanner.nextLine());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
