package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.exceptions.DatabaseSideException;
import org.FishFromSanDiego.cats.exceptions.UserNotFoundException;

import java.util.List;

public interface UserManagementService {
    UserDto registerNewUser(UserDto user) throws DatabaseSideException;
    List<UserDto> getAllUserInfos() throws DatabaseSideException;
    void removeUser(long userId) throws DatabaseSideException, UserNotFoundException;
}
