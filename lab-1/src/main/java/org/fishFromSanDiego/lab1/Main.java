package org.fishFromSanDiego.lab1;

import org.fishFromSanDiego.lab1.models.Client;
import org.fishFromSanDiego.lab1.models.TransactionInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var a = scanner.nextLine();
        var opt = Optional.of(12);
        opt.ifPresent(System.out::println);
        var builder = Client.builder();
        var builder1 = builder
                .withFullName("A", "a");
        var client2 = Client.builder().withFullName("b", "b")
                .build();
        var client1 = builder1.build();
        System.out.println(client1.toString());
        System.out.println(client2.toString());
    }
}
