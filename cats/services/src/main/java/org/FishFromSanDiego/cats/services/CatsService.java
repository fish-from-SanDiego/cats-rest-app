package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.CatDto;

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

    List<CatDto> getAllCats();

    CatDto registerNewCat(CatDto cat);

    void removeCatById(long id);
    //😿 одумайтесь
}
