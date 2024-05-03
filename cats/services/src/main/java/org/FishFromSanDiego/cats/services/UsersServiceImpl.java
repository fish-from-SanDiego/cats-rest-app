package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.exceptions.UserNotFoundException;
import org.FishFromSanDiego.cats.models.User;
import org.FishFromSanDiego.cats.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UsersServiceImpl implements UsersService {
    private final UserRepository userRepository;

    @Autowired
    public UsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException();
        return user.get().getDto();
    }

    @Transactional(readOnly = false)
    @Override
    public UserDto registerNewUser(UserDto user) {
        return userRepository.save(User.fromDto(user)).getDto();
    }

    @Transactional(readOnly = false)
    @Override
    public void updateUser(UserDto updatedUser) {
        Optional<User> user = userRepository.findById(updatedUser.getId());
        if (user.isEmpty())
            throw new UserNotFoundException();
        user.get().copyFromDto(updatedUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(User::getDto).toList();
    }

    @Transactional(readOnly = false)
    @Override
    public void removeUserById(long id) {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException();
        userRepository.deleteById(id);
    }
}


//
//import org.FishFromSanDiego.cats.dao.DaoContext;
//import org.FishFromSanDiego.cats.dto.CatDto;
//import org.FishFromSanDiego.cats.dto.UserDto;
//import org.FishFromSanDiego.cats.exceptions.CatBelongsToOtherUserException;
//import org.FishFromSanDiego.cats.exceptions.DatabaseSideException;
//import org.FishFromSanDiego.cats.exceptions.NoCatWithSuchIdException;
//import org.FishFromSanDiego.cats.exceptions.NoUserWithSuchIdException;
//import org.FishFromSanDiego.cats.models.Cat;
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