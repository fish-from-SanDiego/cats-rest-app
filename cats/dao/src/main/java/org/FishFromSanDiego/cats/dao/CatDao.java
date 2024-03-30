package org.FishFromSanDiego.cats.dao;

import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.models.Colour;

import java.time.LocalDate;
import java.util.Map;

public interface CatDao {
    long addNewCat(CatDto cat, long ownerId);

    void updateCatInfo(CatDto newInfo, long userId, long catId);

    void updateCatName(String newName, long userId, long catId);

    void updateCatBirthDate(LocalDate newBirthDate, long userId, long catId);

    void updateCatBreed(String newBreed, long userId, long catId);

    void updateCatColour(Colour newColour, long userId, long catId);

    void removeCatById(long catId, long userId);

    void friendOtherCat(long catId, long userId, long friendId);

    void unfriendOtherCat(long catId, long userId, long friendId);

    Map<Long, CatDto> getAllCats();

    Map<Long, CatDto> getAllCatsByUserId(long userId);
}
