package org.FishFromSanDiego.cats.dao;

import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.exceptions.*;
import org.FishFromSanDiego.cats.models.Cat;
import org.FishFromSanDiego.cats.models.Colour;

import java.time.LocalDate;
import java.util.Collection;

public interface CatDao {
    Cat addNewCat(CatDto cat, long ownerId) throws NoUserWithSuchIdException, DatabaseSideException;

    Cat getCatById(long catId, long ownerId)
            throws DatabaseSideException, NoCatWithSuchIdException, CatBelongsToOtherUserException;

    void updateCatInfo(CatDto newInfo, long ownerId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DatabaseSideException;

    void updateCatName(String newName, long ownerId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DatabaseSideException;

    void updateCatBirthDate(LocalDate newBirthDate, long ownerId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DatabaseSideException;

    void updateCatBreed(String newBreed, long ownerId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DatabaseSideException;

    void updateCatColour(Colour newColour, long ownerId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DatabaseSideException;

    void removeCatById(long catId, long ownerId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DatabaseSideException;

    void friendOtherCat(long catId, long ownerId, long friendId) throws
            NoCatFriendWithSuchIdException,
            CatBelongsToOtherUserException,
            NoCatWithSuchIdException,
            DatabaseSideException,
            OtherCatIsAlreadyThisCatFriendException;

    void unfriendOtherCat(long catId, long ownerID, long friendId) throws
            NoCatWithSuchIdException,
            CatBelongsToOtherUserException,
            NoCatFriendWithSuchIdException,
            OtherCatIsNotThisCatFriendException,
            DatabaseSideException;

    Collection<Cat> getAllCats() throws DatabaseSideException;

    Collection<Cat> getAllCatsByUserId(long userId) throws DatabaseSideException;

    Collection<Cat> getCatsForWhomThisIsFriend(long catId, long ownerID)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException;
}
