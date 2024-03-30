package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.exceptions.DatabaseSideException;
import org.FishFromSanDiego.cats.exceptions.NoUserWithSuchIdException;

import java.util.Collection;

public interface UserManagementService {
    UserDto registerNewUser(UserDto user) throws DatabaseSideException;
    Collection<UserDto> getAllUserInfos() throws DatabaseSideException;
    void removeUser(long userId) throws DatabaseSideException, NoUserWithSuchIdException;
}
