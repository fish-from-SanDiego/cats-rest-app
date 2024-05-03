package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.exceptions.*;
import org.FishFromSanDiego.cats.models.Colour;

import java.time.LocalDate;
import java.util.List;

public interface CatsService {
    CatDto getCatById(long id);

    void updateCat(CatDto updatedCat);

    void friendCat(long requestingCatId, long friendCatId);

    void unfriendCat(long requestingCatId, long friendCatId);

    List<CatDto> getCatFriendsById(long id);

    List<CatDto> getCatFriendIncomingInvitesById(long id);

    List<CatDto> getCatFriendOutgoingInvitesById(long id);

    List<CatDto> getCatsByOwnerId(long ownerId);

    CatDto registerNewCat(CatDto cat);

    void removeCatById(long id);
    //😿 одумайтесь
}
