package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.exceptions.CatBelongsToOtherUserException;
import org.FishFromSanDiego.cats.exceptions.DatabaseSideException;
import org.FishFromSanDiego.cats.exceptions.NoCatWithSuchIdException;
import org.FishFromSanDiego.cats.exceptions.NoUserWithSuchIdException;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserDto getUserInfo() throws DatabaseSideException, NoUserWithSuchIdException;

    void setUserFirstName(String newName) throws DatabaseSideException, NoUserWithSuchIdException;

    void setUserSecondName(String newName) throws DatabaseSideException, NoUserWithSuchIdException;

    void setUserBirthDate(LocalDate newBirthdate) throws DatabaseSideException, NoUserWithSuchIdException;

    List<CatDto> getAllCatInfos() throws DatabaseSideException;

    CatDto registerNewCat(CatDto cat) throws DatabaseSideException, NoUserWithSuchIdException;

    void removeCat(long catId) throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException;
    //😿 одумайтесь

    public CatDto getCatById(long catId)
            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException;
}
