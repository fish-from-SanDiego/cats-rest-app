package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.exceptions.CatBelongsToOtherUserException;
import org.FishFromSanDiego.cats.exceptions.DatabaseSideException;
import org.FishFromSanDiego.cats.exceptions.NoCatWithSuchIdException;
import org.FishFromSanDiego.cats.exceptions.NoUserWithSuchIdException;
import org.FishFromSanDiego.cats.models.Cat;

import java.time.LocalDate;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final long userId;
    private final DaoContext daoContext;

    public UserServiceImpl(DaoContext daoContext, long userId) {
        this.userId = userId;
        this.daoContext = daoContext;
    }

    @Override
    public UserDto getUserInfo() throws DatabaseSideException, NoUserWithSuchIdException {
        return daoContext.getUserDao().getUserById(userId).getDto();
    }

    @Override
    public void setUserFirstName(String newName) throws DatabaseSideException, NoUserWithSuchIdException {
        daoContext.getUserDao().updateUserFirstName(newName, userId);
    }

    @Override
    public void setUserSecondName(String newName) throws DatabaseSideException, NoUserWithSuchIdException {
        daoContext.getUserDao().updateUserSecondName(newName, userId);
    }

    @Override
    public void setUserBirthDate(LocalDate newBirthdate) throws DatabaseSideException, NoUserWithSuchIdException {
        daoContext.getUserDao().updateUserBirthDate(newBirthdate, userId);

    }

    @Override
    public List<CatDto> getAllCatInfos() throws DatabaseSideException {
        return daoContext.getCatDao().getAllCatsByUserId(userId).stream().map(Cat::getDto).toList();
    }

    @Override
    public CatDto registerNewCat(CatDto cat) throws DatabaseSideException, NoUserWithSuchIdException {
        return daoContext.getCatDao().addNewCat(cat, userId).getDto();
    }

    @Override
    public void removeCat(long catId)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException {
        daoContext.getCatDao().removeCatById(catId, userId);
    }

    @Override
    public CatDto getCatById(long catId)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException {
        return daoContext.getCatDao().getCatById(catId, userId).getDto();
    }
}
