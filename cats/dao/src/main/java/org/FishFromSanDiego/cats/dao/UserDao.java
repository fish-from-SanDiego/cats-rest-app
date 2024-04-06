package org.FishFromSanDiego.cats.dao;

import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.exceptions.DatabaseSideException;
import org.FishFromSanDiego.cats.exceptions.NoUserWithSuchIdException;
import org.FishFromSanDiego.cats.models.User;

import java.time.LocalDate;
import java.util.List;

public interface UserDao {
    User addNewUser(UserDto user) throws DatabaseSideException;

    User getUserById(long userId) throws NoUserWithSuchIdException, DatabaseSideException;

    void updateUserInfo(UserDto newInfo, long userId) throws DatabaseSideException, NoUserWithSuchIdException;

    void updateUserFirstName(String newName, long userId) throws NoUserWithSuchIdException, DatabaseSideException;

    void updateUserSecondName(String newName, long userId) throws NoUserWithSuchIdException, DatabaseSideException;

    void updateUserBirthDate(LocalDate newBirthDate, long userId) throws NoUserWithSuchIdException,
            DatabaseSideException;

    void removeUserById(long userId) throws DatabaseSideException, NoUserWithSuchIdException;

    List<User> getAllUsers() throws DatabaseSideException;
}
