package org.FishFromSanDiego.cats.dao;

import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.exceptions.*;
import org.FishFromSanDiego.cats.models.Cat;
import org.FishFromSanDiego.cats.models.Colour;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public interface CatDao {
    Cat addNewCat(CatDto cat, long ownerId) throws NoUserWithSuchIdException, DaoException;

    void updateCatInfo(CatDto newInfo, long userId, long catId) throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DaoException;

    void updateCatName(String newName, long userId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DaoException;

    void updateCatBirthDate(LocalDate newBirthDate, long userId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DaoException;

    void updateCatBreed(String newBreed, long userId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DaoException;

    void updateCatColour(Colour newColour, long userId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DaoException;

    void removeCatById(long catId, long userId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DaoException;

    void friendOtherCat(long catId, long userId, long friendId)
            throws NoCatFriendWithSuchIdException, CatBelongsToOtherUserException, NoCatWithSuchIdException, DaoException, OtherCatIsAlreadyThisCatFriendException;

    void unfriendOtherCat(long catId, long userId, long friendId) throws
            NoCatWithSuchIdException,
            CatBelongsToOtherUserException,
            NoCatFriendWithSuchIdException,
            OtherCatIsNotThisCatFriendException,
            DaoException;

    Collection<Cat> getAllCats() throws DaoException;

    Collection<Cat> getAllCatsByUserId(long userId) throws DaoException;
}
