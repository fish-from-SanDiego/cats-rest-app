package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dao.DaoContext;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.exceptions.*;
import org.FishFromSanDiego.cats.models.Cat;
import org.FishFromSanDiego.cats.models.Colour;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

public class CatServiceImpl implements CatService {
    private final DaoContext daoContext;
    private final long catId;
    private final long ownerId;

    public CatServiceImpl(DaoContext daoContext, long catId, long ownerId) {
        this.daoContext = daoContext;
        this.catId = catId;
        this.ownerId = ownerId;
    }


    @Override
    public CatDto getCatInfo() throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException {
        return daoContext.getCatDao().getCatById(catId, ownerId).getDto();
    }

    @Override
    public void setCatName(String newName)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException {
        daoContext.getCatDao().updateCatName(newName, ownerId, catId);
    }

    @Override
    public void setCatBirthDate(LocalDate birthDate)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException {
        daoContext.getCatDao().updateCatBirthDate(birthDate, ownerId, catId);

    }

    @Override
    public void setCatBreed(String newBreed)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException {
        daoContext.getCatDao().updateCatBreed(newBreed, ownerId, catId);
    }

    @Override
    public void setCatColour(Colour newColour)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException {
        daoContext.getCatDao().updateCatColour(newColour, ownerId, catId);
    }

    @Override
    public void friendCat(long catId) throws
            NoCatFriendWithSuchIdException,
            DatabaseSideException,
            OtherCatIsAlreadyThisCatFriendException,
            CatBelongsToOtherUserException,
            NoCatWithSuchIdException {
        daoContext.getCatDao().friendOtherCat(this.catId, ownerId, catId);
    }

    @Override
    public void unfriendCat(long catId) throws
            NoCatFriendWithSuchIdException,
            OtherCatIsNotThisCatFriendException,
            DatabaseSideException,
            CatBelongsToOtherUserException,
            NoCatWithSuchIdException {
        daoContext.getCatDao().unfriendOtherCat(this.catId, ownerId, catId);

    }

    @Override
    public Collection<CatDto> getCatFriends()
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException {
        var dao = daoContext.getCatDao();
        var intersection = new HashSet<>(dao.getCatsForWhomThisIsFriend(catId, ownerId));
        intersection.retainAll(new HashSet<>(dao.getCatById(catId, ownerId).getFriends()));
        return intersection.stream().map(Cat::getDto).toList();
    }

    @Override
    public Collection<CatDto> getCatFriendIncomingInvites()
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException {
        var dao = daoContext.getCatDao();
        var difference = new HashSet<>(dao.getCatsForWhomThisIsFriend(catId, ownerId));
        difference.removeAll(new HashSet<>(dao.getCatById(catId, ownerId).getFriends()));
        return difference.stream().map(Cat::getDto).toList();
    }

    @Override
    public Collection<CatDto> getCatFriendOutgoingInvites()
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException {
        var dao = daoContext.getCatDao();
        var difference = new HashSet<>(dao.getCatById(catId, ownerId).getFriends());
        difference.removeAll(new HashSet<>(dao.getCatsForWhomThisIsFriend(catId, ownerId)));
        return difference.stream().map(Cat::getDto).toList();
    }
}
