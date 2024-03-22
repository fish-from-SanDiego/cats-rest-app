package org.fishFromSanDiego.lab1.presentation.implementations.input;

import org.fishFromSanDiego.lab1.presentation.abstractions.input.InputAsker;

import java.util.Optional;
import java.util.Scanner;

public class ScannerIntInputAsker implements InputAsker<Integer> {
    private final Scanner scanner;

    public ScannerIntInputAsker(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Optional<Integer> Ask(String prompt) {
        System.out.print(prompt);
        try {
            return Optional.of(scanner.nextInt());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
