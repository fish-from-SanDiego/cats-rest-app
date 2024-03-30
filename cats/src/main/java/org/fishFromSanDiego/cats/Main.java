package org.fishFromSanDiego.cats;

import jakarta.persistence.Persistence;
import org.FishFromSanDiego.cats.dao.CatDaoImpl;
import org.FishFromSanDiego.cats.dao.UserDaoImpl;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.models.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("cats-db");
        var uRepo = new UserDaoImpl(emf);
        var cRepo = new CatDaoImpl(emf);
        var userDto = UserDto.builder().firstName("4").secondName("bbb").build();
        User user;
        var catDto = CatDto.builder().name("catty").build();
        try {
//            var cat = cRepo.addNewCat(catDto, 2);
//            cRepo.friendOtherCat(cat.getId(), 2, 8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        new Scanner(System.in).nextLine();
    }
}

