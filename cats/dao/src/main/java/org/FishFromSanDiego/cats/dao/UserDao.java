package org.FishFromSanDiego.cats.dao;

import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.exceptions.DaoException;
import org.FishFromSanDiego.cats.exceptions.NoUserWithSuchIdException;
import org.FishFromSanDiego.cats.models.User;

import java.time.LocalDate;
import java.util.Collection;

public interface UserDao {
    User addNewUser(UserDto user) throws DaoException;

    User getUserById(long userId) throws NoUserWithSuchIdException, DaoException;

    void updateUserInfo(UserDto newInfo, long userId) throws DaoException, NoUserWithSuchIdException;

    void updateUserFirstName(String newName, long userId) throws NoUserWithSuchIdException, DaoException;

    void updateUserSecondName(String newName, long userId) throws NoUserWithSuchIdException, DaoException;

    void updateUserBirthDate(LocalDate newBirthDate, long userId) throws NoUserWithSuchIdException, DaoException;

    void removeUserById(long userId) throws DaoException, NoUserWithSuchIdException;

    Collection<User> getAllUsers() throws DaoException;
}
