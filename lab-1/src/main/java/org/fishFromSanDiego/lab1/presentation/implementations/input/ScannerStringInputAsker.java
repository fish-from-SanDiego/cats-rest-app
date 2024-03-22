package org.fishFromSanDiego.lab1.presentation.implementations.input;

import org.fishFromSanDiego.lab1.presentation.abstractions.input.InputAsker;

import java.math.BigDecimal;
import java.util.Scanner;

public class ScannerStringInputAsker implements InputAsker<String> {
    private final Scanner scanner;

    public ScannerStringInputAsker(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String Ask(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
