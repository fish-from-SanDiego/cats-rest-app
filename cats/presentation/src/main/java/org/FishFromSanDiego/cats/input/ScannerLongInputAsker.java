package org.FishFromSanDiego.cats.input;

import java.util.Optional;
import java.util.Scanner;

public class ScannerLongInputAsker implements InputAsker<Long> {
    private final Scanner scanner;

    public ScannerLongInputAsker(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Optional<Long> Ask(String prompt) {
        System.out.print(prompt);
        try {
            var integer = scanner.nextLong();
            scanner.nextLine();
            return Optional.of(integer);
        } catch (Exception e) {
            scanner.nextLine();
            return Optional.empty();
        }
    }
}
