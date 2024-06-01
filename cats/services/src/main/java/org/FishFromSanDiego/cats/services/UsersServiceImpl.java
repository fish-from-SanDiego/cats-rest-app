package org.FishFromSanDiego.cats.services;

import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.exceptions.UserNotFoundException;
import org.FishFromSanDiego.cats.exceptions.UserWithSuchUsernameAlreadyExistsException;
import org.FishFromSanDiego.cats.models.ApplicationUser;
import org.FishFromSanDiego.cats.models.User;
import org.FishFromSanDiego.cats.repositories.ApplicationUsersRepository;
import org.FishFromSanDiego.cats.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final ApplicationUsersRepository applicationUsersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, ApplicationUsersRepository applicationUsersRepository) {
        this.usersRepository = usersRepository;
        this.applicationUsersRepository = applicationUsersRepository;
    }

    @Override
    public UserDto getUserById(long id) {
        User user = usersRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return user.getDto();
    }

    @Transactional(readOnly = false)
    @Override
    public UserDto registerNewUser(UserDto user) {
        if (applicationUsersRepository.existsByUsername(user.getUsername()))
            throw new UserWithSuchUsernameAlreadyExistsException();
        var userEntity = User.fromDto(user);
        var applicationUser = applicationUsersRepository.save(ApplicationUser.fromDto(user));
        userEntity.setApplicationUser(applicationUser);
        userEntity.setId(applicationUser.getId());
        return usersRepository.save(userEntity).getDto();
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
        applicationUsersRepository.deleteById(id);
    }
}
