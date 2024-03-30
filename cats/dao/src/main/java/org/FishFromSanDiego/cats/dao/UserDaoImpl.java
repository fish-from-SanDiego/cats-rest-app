package org.FishFromSanDiego.cats.dao;

import jakarta.persistence.EntityManagerFactory;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.exceptions.DatabaseSideException;
import org.FishFromSanDiego.cats.exceptions.NoUserWithSuchIdException;
import org.FishFromSanDiego.cats.models.User;
import org.FishFromSanDiego.cats.util.Helper;

import java.time.LocalDate;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final EntityManagerFactory entityManagerFactory;

    public UserDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public User addNewUser(UserDto user) throws DatabaseSideException {
        var newUser = User.fromDto(user);
        try {
            Helper.inTransaction(entityManagerFactory, em -> em.persist(newUser));
        } catch (Exception e) {
            throw new DatabaseSideException();
        }
        return newUser;
    }

    @Override
    public User getUserById(long userId) throws NoUserWithSuchIdException, DatabaseSideException {
        User user;
        try {
            var em = entityManagerFactory.createEntityManager();
            user = em.find(User.class, userId);
        } catch (Exception e) {
            throw new DatabaseSideException();
        }
        if (user == null)
            throw new NoUserWithSuchIdException();
        return user;
    }

    @Override
    public void updateUserInfo(UserDto newInfo, long userId) throws DatabaseSideException, NoUserWithSuchIdException {
        try {
            Helper.inTransaction(entityManagerFactory, em -> {
                var user = em.find(User.class, userId);
                if (user == null)
                    throw new RuntimeException(new NoUserWithSuchIdException());
                user.copyFromDto(newInfo);
            });

        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getClass().equals(NoUserWithSuchIdException.class))
                throw new NoUserWithSuchIdException();
            throw new DatabaseSideException();
        }
    }

    @Override
    public void updateUserFirstName(String newName, long userId) throws NoUserWithSuchIdException,
            DatabaseSideException {
        try {
            Helper.inTransaction(entityManagerFactory, em -> {
                var user = em.find(User.class, userId);
                if (user == null)
                    throw new RuntimeException(new NoUserWithSuchIdException());
                user.setFirstName(newName);
            });

        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getClass().equals(NoUserWithSuchIdException.class))
                throw new NoUserWithSuchIdException();
            throw new DatabaseSideException();
        }
    }

    @Override
    public void updateUserSecondName(String newName, long userId) throws NoUserWithSuchIdException,
            DatabaseSideException {
        try {
            Helper.inTransaction(entityManagerFactory, em -> {
                var user = em.find(User.class, userId);
                if (user == null)
                    throw new RuntimeException(new NoUserWithSuchIdException());
                user.setSecondName(newName);
            });

        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getClass().equals(NoUserWithSuchIdException.class))
                throw new NoUserWithSuchIdException();
            throw new DatabaseSideException();
        }
    }

    @Override
    public void updateUserBirthDate(LocalDate newBirthDate, long userId)
            throws NoUserWithSuchIdException, DatabaseSideException {
        try {
            Helper.inTransaction(entityManagerFactory, em -> {
                var user = em.find(User.class, userId);
                if (user == null)
                    throw new RuntimeException(new NoUserWithSuchIdException());
                user.setBirthDate(newBirthDate);
            });

        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getClass().equals(NoUserWithSuchIdException.class))
                throw new NoUserWithSuchIdException();
            throw new DatabaseSideException();
        }
    }

    @Override
    public void removeUserById(long userId) throws DatabaseSideException, NoUserWithSuchIdException {
        try {
            Helper.inTransaction(entityManagerFactory, em -> {
                var user = em.find(User.class, userId);
                if (user == null)
                    throw new RuntimeException(new NoUserWithSuchIdException());
                em.remove(user);
            });
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getClass().equals(NoUserWithSuchIdException.class))
                throw new NoUserWithSuchIdException();
            throw new DatabaseSideException();
        }
    }

    @Override
    public List<User> getAllUsers() throws DatabaseSideException {
        try {
            var em = entityManagerFactory.createEntityManager();
            return em.createQuery("FROM User", User.class).getResultList();
        } catch (Exception e) {
            throw new DatabaseSideException();
        }
    }
}
