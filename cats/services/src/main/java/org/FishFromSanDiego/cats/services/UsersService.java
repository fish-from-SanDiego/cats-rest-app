package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.UserDto;

import java.util.List;

public interface UsersService {
    UserDto getUserById(long id);

    UserDto registerNewUser(UserDto user);

    void updateUser(UserDto updatedUser);

    List<UserDto> getAllUsers();

    void removeUserById(long id);
}
