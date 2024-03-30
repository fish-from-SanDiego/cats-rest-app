package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.exceptions.*;
import org.FishFromSanDiego.cats.models.Colour;

import java.time.LocalDate;
import java.util.Collection;

public interface CatService {
    CatDto getCatInfo() throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException;

    void setCatName(String newName)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException;

    void setCatBirthDate(LocalDate birthDate)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException;

    void setCatBreed(String newBreed)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException;

    void setCatColour(Colour newColour)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException;

    void friendCat(long catId) throws
            NoCatFriendWithSuchIdException,
            DatabaseSideException,
            OtherCatIsAlreadyThisCatFriendException,
            CatBelongsToOtherUserException,
            NoCatWithSuchIdException;

    void unfriendCat(long catId) throws
            NoCatFriendWithSuchIdException,
            OtherCatIsNotThisCatFriendException,
            DatabaseSideException,
            CatBelongsToOtherUserException,
            NoCatWithSuchIdException;

    Collection<CatDto> getCatFriends()
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException;

    Collection<CatDto> getCatFriendIncomingInvites()
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException;

    Collection<CatDto> getCatFriendOutgoingInvites()
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException;
}
