package org.fishFromSanDiego.lab1.presentation.implementations.input;

import org.fishFromSanDiego.lab1.presentation.abstractions.input.InputAsker;

import java.util.Optional;
import java.util.Scanner;

/**
 * The type Scanner int input asker.
 */
public class ScannerIntInputAsker implements InputAsker<Integer> {
    private final Scanner scanner;

    /**
     * Instantiates a new Scanner int input asker.
     *
     * @param scanner the scanner
     */
    public ScannerIntInputAsker(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Optional<Integer> Ask(String prompt) {
        System.out.print(prompt);
        try {
            var integer = scanner.nextInt();
            scanner.nextLine();
            return Optional.of(integer);
        } catch (Exception e) {
            scanner.nextLine();
            return Optional.empty();
        }
    }
}
