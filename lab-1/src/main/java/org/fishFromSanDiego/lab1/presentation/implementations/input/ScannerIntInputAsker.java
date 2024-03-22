package org.fishFromSanDiego.lab1.presentation.implementations.input;

import org.fishFromSanDiego.lab1.presentation.abstractions.input.InputAsker;

import java.util.Scanner;

public class ScannerIntInputAsker implements InputAsker<Integer> {
    private final Scanner scanner;

    public ScannerIntInputAsker(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Integer Ask(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }
}
