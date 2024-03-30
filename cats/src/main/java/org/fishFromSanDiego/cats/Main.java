package org.fishFromSanDiego.cats;


import org.FishFromSanDiego.cats.dao.PostgresCatDao;
import org.FishFromSanDiego.cats.dao.PostgresUserDao;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.models.Colour;
import org.FishFromSanDiego.cats.models.User;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        var str = Colour.BLACK.toString();
        var uRepo = new PostgresUserDao();
        var cRepo = new PostgresCatDao();
        var userDto = UserDto.builder().firstName("555").secondName("bbb").build();
        User user;
        var catDto = CatDto.builder().name("kitty").build();
        try {
            uRepo.addNewUser(userDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        new Scanner(System.in).nextLine();
    }
}

