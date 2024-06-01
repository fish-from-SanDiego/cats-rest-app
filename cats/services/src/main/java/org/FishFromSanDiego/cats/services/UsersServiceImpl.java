package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.exceptions.UserNotFoundException;
import org.FishFromSanDiego.cats.models.User;
import org.FishFromSanDiego.cats.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDto getUserById(long id) {
        User user = usersRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return user.getDto();
    }

    @Transactional(readOnly = false)
    @Override
    public UserDto registerNewUser(UserDto user) {
        return usersRepository.save(User.fromDto(user)).getDto();
    }

    @Transactional(readOnly = false)
    @Override
    public void updateUser(UserDto updatedUser) {
        User user = usersRepository.findById(updatedUser.getId()).orElseThrow(UserNotFoundException::new);
        usersRepository.save(user.copyFromDto(updatedUser));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return usersRepository.findAll().stream().map(User::getDto).toList();
    }

    @Transactional(readOnly = false)
    @Override
    public void removeUserById(long id) {
        if (!usersRepository.existsById(id))
            throw new UserNotFoundException();
        usersRepository.deleteById(id);
    }
}


//
//import dao.org.fishFromSanDiego.cats.DaoContext;
//import dto.org.fishFromSanDiego.cats.CatDto;
//import dto.org.fishFromSanDiego.cats.UserDto;
//import exceptions.org.fishFromSanDiego.cats.CatBelongsToOtherUserException;
//import exceptions.org.fishFromSanDiego.cats.DatabaseSideException;
//import exceptions.org.fishFromSanDiego.cats.NoCatWithSuchIdException;
//import org.FishFromSanDiego.cats.exceptions.NoUserWithSuchIdException;
//import models.org.fishFromSanDiego.cats.Cat;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class UserServiceImpl implements UserService {
//    private final long userId;
//    private final DaoContext daoContext;
//
//    public UserServiceImpl(DaoContext daoContext, long userId) {
//        this.userId = userId;
//        this.daoContext = daoContext;
//    }
//
//    @Override
//    public UserDto getUserInfo() throws DatabaseSideException, NoUserWithSuchIdException {
//        return daoContext.getUserDao().getUserById(userId).getDto();
//    }
//
//    @Override
//    public void setUserFirstName(String newName) throws DatabaseSideException, NoUserWithSuchIdException {
//        daoContext.getUserDao().updateUserFirstName(newName, userId);
//    }
//
//    @Override
//    public void setUserSecondName(String newName) throws DatabaseSideException, NoUserWithSuchIdException {
//        daoContext.getUserDao().updateUserSecondName(newName, userId);
//    }
//
//    @Override
//    public void setUserBirthDate(LocalDate newBirthdate) throws DatabaseSideException, NoUserWithSuchIdException {
//        daoContext.getUserDao().updateUserBirthDate(newBirthdate, userId);
//
//    }
//
//    @Override
//    public List<CatDto> getAllCatInfos() throws DatabaseSideException {
//        return daoContext.getCatDao().getAllCatsByUserId(userId).stream().map(Cat::getDto).toList();
//    }
//
//    @Override
//    public CatDto registerNewCat(CatDto cat) throws DatabaseSideException, NoUserWithSuchIdException {
//        return daoContext.getCatDao().addNewCat(cat, userId).getDto();
//    }
//
//    @Override
//    public void removeCat(long catId)
//            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException {
//        daoContext.getCatDao().removeCatById(catId, userId);
//    }
//
//    @Override
//    public CatDto getCatById(long catId)
//            throws DatabaseSideException, CatBelongsToOtherUserException, NoCatWithSuchIdException {
//        return daoContext.getCatDao().getCatById(catId, userId).getDto();
//    }
//}