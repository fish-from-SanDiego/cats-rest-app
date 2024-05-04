package org.FishFromSanDiego.cats.dao;

import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.exceptions.*;
import org.FishFromSanDiego.cats.models.Cat;

import java.util.List;

@Deprecated
public interface CatDao {
    Cat addNewCat(CatDto cat, long ownerId) throws UserNotFoundException, DatabaseSideException;

    Cat getCatById(long catId, long ownerId)
            throws DatabaseSideException, NoCatWithSuchIdException, CatBelongsToOtherUserException;

    void updateCatInfo(CatDto newInfo, long ownerId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DatabaseSideException;

    void removeCatById(long catId, long ownerId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DatabaseSideException;

    void friendOtherCat(long catId, long ownerId, long friendId) throws
            CatNotFoundException,
            CatBelongsToOtherUserException,
            NoCatWithSuchIdException,
            DatabaseSideException, CatAlreadyFriendedException, CantFriendSelfException;

    void unfriendOtherCat(long catId, long ownerID, long friendId) throws
            NoCatWithSuchIdException,
            CatBelongsToOtherUserException,
            CatNotFoundException, CatsAreNotFriendsException,
            DatabaseSideException;

    List<Cat> getAllCats() throws DatabaseSideException;

    List<Cat> getAllCatsByUserId(long userId) throws DatabaseSideException;

    List<Cat> getCatsForWhomThisIsFriend(long catId, long ownerID)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException;
}
