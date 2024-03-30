package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.exceptions.DatabaseSideException;
import org.FishFromSanDiego.cats.exceptions.NoUserWithSuchIdException;
import org.FishFromSanDiego.cats.models.User;

import java.util.List;

public class UserManagementServiceImpl implements UserManagementService {
    private final DaoContext daoContext;

    public UserManagementServiceImpl(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    @Override
    public UserDto registerNewUser(UserDto user) throws DatabaseSideException {
        return daoContext.getUserDao().addNewUser(user).getDto();
    }

    @Override
    public List<UserDto> getAllUserInfos() throws DatabaseSideException {
        return daoContext.getUserDao().getAllUsers().stream().map(User::getDto).toList();
    }

    @Override
    public void removeUser(long userId) throws DatabaseSideException, NoUserWithSuchIdException {
        daoContext.getUserDao().removeUserById(userId);
    }
}
