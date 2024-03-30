package org.FishFromSanDiego.cats.input;

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
            var integer = scanner.nextInt();
            scanner.nextLine();
            return Optional.of(integer);
        } catch (Exception e) {
            scanner.nextLine();
            return Optional.empty();
        }
    }
}
