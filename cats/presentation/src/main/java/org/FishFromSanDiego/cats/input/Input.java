package org.FishFromSanDiego.cats.input;

import java.util.Optional;
import java.util.Scanner;

public class Input {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputAsker<String> stringInputAsker = new ScannerStringInputAsker(scanner);
    private static final InputAsker<Long> longInputAsker = new ScannerLongInputAsker(scanner);
    private static final InputAsker<Integer> intInputAsker = new ScannerIntInputAsker(scanner);

    public static Optional<Long> AskLong(String prompt) {
        return longInputAsker.Ask(prompt);
    }

    public static Optional<String> AskString(String prompt) {
        return stringInputAsker.Ask(prompt);
    }

    public static Optional<Integer> AskInt(String prompt) {
        return intInputAsker.Ask(prompt);
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
