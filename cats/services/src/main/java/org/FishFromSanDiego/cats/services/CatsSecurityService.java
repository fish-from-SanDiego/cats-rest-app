package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.ApplicationUserDetails;
import org.FishFromSanDiego.cats.models.UserRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatsSecurityService {
    private CatsService catsService;

    @Autowired
    public CatsSecurityService(CatsService catsService) {
        this.catsService = catsService;
    }


    public boolean isAdminOrOwner(ApplicationUserDetails userDetails, long catId) {
        if (userDetails.getAuthorities().contains(UserRoleType.ROLE_ADMIN)) {
            return true;
        }
        var realCat = catsService.getCatById(catId);
        return realCat.getOwnerId() == userDetails.getId();
    }

}
