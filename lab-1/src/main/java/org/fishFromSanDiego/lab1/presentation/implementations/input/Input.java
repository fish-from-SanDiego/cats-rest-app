package org.fishFromSanDiego.lab1.presentation.implementations.input;

import org.fishFromSanDiego.lab1.presentation.abstractions.input.InputAsker;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

public class Input {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputAsker<String> stringInputAsker = new ScannerStringInputAsker(scanner);
    private static final InputAsker<BigDecimal> bigDecimalInputAsker = new ScannerBigDecimalInputAsker(scanner);
    private static final InputAsker<Integer> integerInputAsker = new ScannerIntInputAsker(scanner);

    public static Optional<Integer> AskInt(String prompt) {
        return integerInputAsker.Ask(prompt);
    }

    public static Optional<BigDecimal> AskBigDecimal(String prompt) {
        return bigDecimalInputAsker.Ask(prompt);
    }

    public static Optional<String> AskString(String prompt) {
        return stringInputAsker.Ask(prompt);
    }

    public static void WaitTillEnterPress() {
        System.out.println("Press Enter to continue...");
        try {
            //noinspection ResultOfMethodCallIgnored
            System.in.read();
        } catch (Exception ignored) {
        }

    }

}
