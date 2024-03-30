package org.FishFromSanDiego.cats.dao;

import org.FishFromSanDiego.cats.dto.UserDto;

import java.time.LocalDate;

public interface UserDao {
    long addNewUser(UserDto user);

    UserDto getUserInfoById(long userId);

    void updateUserInfo(UserDto newInfo, long userId);

    void updateUserFirstName(String newName, long userId);

    void updateUserSecondName(String newName, long userId);

    void updateUserBirthDate(LocalDate newBirthDate, long userId);

    void removeUserById(long userId);
}
