package org.FishFromSanDiego.cats.input;

import java.util.Optional;
import java.util.Scanner;

public class ScannerStringInputAsker implements InputAsker<String> {
    private final Scanner scanner;

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
