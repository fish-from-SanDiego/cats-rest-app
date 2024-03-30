package org.FishFromSanDiego.cats.dao;

import jakarta.persistence.EntityManagerFactory;
import org.FishFromSanDiego.cats.dto.CatDto;
import org.FishFromSanDiego.cats.exceptions.*;
import org.FishFromSanDiego.cats.models.Cat;
import org.FishFromSanDiego.cats.models.Colour;
import org.FishFromSanDiego.cats.models.User;
import org.FishFromSanDiego.cats.util.Helper;

import java.time.LocalDate;
import java.util.Collection;

public class CatDaoImpl implements CatDao {
    private final EntityManagerFactory entityManagerFactory;

    public CatDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Cat addNewCat(CatDto cat, long ownerId) throws NoUserWithSuchIdException, DaoException {
        var newCat = Cat.fromDto(cat);
        try {
            Helper.inTransaction(entityManagerFactory, em -> {
                User owner = em.find(User.class, ownerId);
                if (owner == null)
                    throw new RuntimeException(new NoUserWithSuchIdException());
                newCat.setOwner(owner);
                em.persist(newCat);
            });
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getClass().equals(NoUserWithSuchIdException.class))
                throw new NoUserWithSuchIdException();
            else
                throw new DaoException();
        }

        return newCat;
    }

    @Override
    public void updateCatInfo(CatDto newInfo, long userId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DaoException {
        try {
            Helper.inTransaction(entityManagerFactory, em -> {
                Cat cat = em.find(Cat.class, catId);
                if (cat == null)
                    throw new RuntimeException(new NoCatWithSuchIdException());
                if (cat.getOwner().getId() != userId)
                    throw new RuntimeException(new CatBelongsToOtherUserException());
                cat.copyFromDto(newInfo);
            });
        } catch (Exception e) {
            if (e.getCause() != null) {
                var clazz = e.getCause().getClass();
                if (clazz.equals(NoCatWithSuchIdException.class))
                    throw new NoCatWithSuchIdException();
                if (clazz.equals(CatBelongsToOtherUserException.class))
                    throw new CatBelongsToOtherUserException();
            }
            throw new DaoException();
        }
    }

    @Override
    public void updateCatName(String newName, long userId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DaoException {
        try {

            Helper.inTransaction(entityManagerFactory, em -> {
                Cat cat = em.find(Cat.class, catId);
                if (cat == null)
                    throw new RuntimeException(new NoCatWithSuchIdException());
                if (cat.getOwner().getId() != userId)
                    throw new RuntimeException(new CatBelongsToOtherUserException());
                cat.setName(newName);
            });
        } catch (Exception e) {
            if (e.getCause() != null) {
                var clazz = e.getCause().getClass();
                if (clazz.equals(NoCatWithSuchIdException.class))
                    throw new NoCatWithSuchIdException();
                if (clazz.equals(CatBelongsToOtherUserException.class))
                    throw new CatBelongsToOtherUserException();
            }
            throw new DaoException();
        }
    }

    @Override
    public void updateCatBirthDate(LocalDate newBirthDate, long userId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DaoException {
        try {

            Helper.inTransaction(entityManagerFactory, em -> {
                Cat cat = em.find(Cat.class, catId);
                if (cat == null)
                    throw new RuntimeException(new NoCatWithSuchIdException());
                if (cat.getOwner().getId() != userId)
                    throw new RuntimeException(new CatBelongsToOtherUserException());
                cat.setBirthDate(newBirthDate);
            });
        } catch (Exception e) {
            if (e.getCause() != null) {
                var clazz = e.getCause().getClass();
                if (clazz.equals(NoCatWithSuchIdException.class))
                    throw new NoCatWithSuchIdException();
                if (clazz.equals(CatBelongsToOtherUserException.class))
                    throw new CatBelongsToOtherUserException();
            }
            throw new DaoException();
        }
    }

    @Override
    public void updateCatBreed(String newBreed, long userId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DaoException {
        try {

            Helper.inTransaction(entityManagerFactory, em -> {
                Cat cat = em.find(Cat.class, catId);
                if (cat == null)
                    throw new RuntimeException(new NoCatWithSuchIdException());
                if (cat.getOwner().getId() != userId)
                    throw new RuntimeException(new CatBelongsToOtherUserException());
                cat.setBreed(newBreed);
            });
        } catch (Exception e) {
            if (e.getCause() != null) {
                var clazz = e.getCause().getClass();
                if (clazz.equals(NoCatWithSuchIdException.class))
                    throw new NoCatWithSuchIdException();
                if (clazz.equals(CatBelongsToOtherUserException.class))
                    throw new CatBelongsToOtherUserException();
            }
            throw new DaoException();
        }
    }

    @Override
    public void updateCatColour(Colour newColour, long userId, long catId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DaoException {
        try {

            Helper.inTransaction(entityManagerFactory, em -> {
                Cat cat = em.find(Cat.class, catId);
                if (cat == null)
                    throw new RuntimeException(new NoCatWithSuchIdException());
                if (cat.getOwner().getId() != userId)
                    throw new RuntimeException(new CatBelongsToOtherUserException());
                cat.setColour(newColour);
            });
        } catch (Exception e) {
            if (e.getCause() != null) {
                var clazz = e.getCause().getClass();
                if (clazz.equals(NoCatWithSuchIdException.class))
                    throw new NoCatWithSuchIdException();
                if (clazz.equals(CatBelongsToOtherUserException.class))
                    throw new CatBelongsToOtherUserException();
            }
            throw new DaoException();
        }
    }

    @Override
    public void removeCatById(long catId, long userId)
            throws NoCatWithSuchIdException, CatBelongsToOtherUserException, DaoException {
        try {

            Helper.inTransaction(entityManagerFactory, em -> {
                Cat cat = em.find(Cat.class, catId);
                if (cat == null)
                    throw new RuntimeException(new NoCatWithSuchIdException());
                if (cat.getOwner().getId() != userId)
                    throw new RuntimeException(new CatBelongsToOtherUserException());
                em.remove(cat);
            });
        } catch (Exception e) {
            if (e.getCause() != null) {
                var clazz = e.getCause().getClass();
                if (clazz.equals(NoCatWithSuchIdException.class))
                    throw new NoCatWithSuchIdException();
                if (clazz.equals(CatBelongsToOtherUserException.class))
                    throw new CatBelongsToOtherUserException();
            }
            throw new DaoException();
        }
    }

    @Override
    public void friendOtherCat(long catId, long userId, long friendId) throws
            NoCatFriendWithSuchIdException,
            CatBelongsToOtherUserException,
            NoCatWithSuchIdException,
            DaoException,
            OtherCatIsAlreadyThisCatFriendException {
        try {

            Helper.inTransaction(entityManagerFactory, em -> {
                Cat cat = em.find(Cat.class, catId);
                Cat friend = em.find(Cat.class, friendId);
                if (cat == null)
                    throw new RuntimeException(new NoCatWithSuchIdException());
                if (friend == null)
                    throw new RuntimeException(new NoCatFriendWithSuchIdException());
                if (cat.getOwner().getId() != userId)
                    throw new RuntimeException(new CatBelongsToOtherUserException());
                if (cat.getFriends().contains(friend))
                    throw new RuntimeException(new OtherCatIsAlreadyThisCatFriendException());
                cat.getFriends().add(friend);
                if (cat.getOwner().getId() == friend.getOwner().getId() && !friend.getFriends().contains(cat))
                    friend.getFriends().add(cat);
            });
        } catch (Exception e) {
            if (e.getCause() != null) {
                var clazz = e.getCause().getClass();
                if (clazz.equals(NoCatWithSuchIdException.class))
                    throw new NoCatWithSuchIdException();
                if (clazz.equals(CatBelongsToOtherUserException.class))
                    throw new CatBelongsToOtherUserException();
                if (clazz.equals(NoCatFriendWithSuchIdException.class))
                    throw new NoCatFriendWithSuchIdException();
                if (clazz.equals(OtherCatIsAlreadyThisCatFriendException.class))
                    throw new OtherCatIsAlreadyThisCatFriendException();
            }
            throw new DaoException();
        }
    }

    @Override
    public void unfriendOtherCat(long catId, long userId, long friendId) throws
            NoCatWithSuchIdException,
            CatBelongsToOtherUserException,
            NoCatFriendWithSuchIdException,
            OtherCatIsNotThisCatFriendException,
            DaoException {
        try {

            Helper.inTransaction(entityManagerFactory, em -> {
                Cat cat = em.find(Cat.class, catId);
                Cat friend = em.find(Cat.class, friendId);
                if (cat == null)
                    throw new RuntimeException(new NoCatWithSuchIdException());
                if (friend == null)
                    throw new RuntimeException(new NoCatFriendWithSuchIdException());
                if (cat.getOwner().getId() != userId)
                    throw new RuntimeException(new CatBelongsToOtherUserException());
                if (!cat.getFriends().contains(friend))
                    throw new RuntimeException(new OtherCatIsNotThisCatFriendException());
                cat.getFriends().remove(friend);
                if (cat.getOwner().getId() == friend.getOwner().getId() && friend.getFriends().contains(cat))
                    friend.getFriends().remove(cat);
            });
        } catch (Exception e) {
            if (e.getCause() != null) {
                var clazz = e.getCause().getClass();
                if (clazz.equals(NoCatWithSuchIdException.class))
                    throw new NoCatWithSuchIdException();
                if (clazz.equals(CatBelongsToOtherUserException.class))
                    throw new CatBelongsToOtherUserException();
                if (clazz.equals(NoCatFriendWithSuchIdException.class))
                    throw new NoCatFriendWithSuchIdException();
                if (clazz.equals(OtherCatIsNotThisCatFriendException.class))
                    throw new OtherCatIsNotThisCatFriendException();
            }
            throw new DaoException();
        }
    }

    @Override
    public Collection<Cat> getAllCats() throws DaoException {
        try {
            var em = entityManagerFactory.createEntityManager();
            return em.createQuery("FROM Cat", Cat.class).getResultList();
        } catch (Exception e) {
            throw new DaoException();
        }
    }

    @Override
    public Collection<Cat> getAllCatsByUserId(long userId) throws DaoException {
        try {
            var em = entityManagerFactory.createEntityManager();
            return em.createQuery("FROM Cat WHERE id = :arg", Cat.class).setParameter("arg", userId).getResultList();
        } catch (Exception e) {
            throw new DaoException();
        }
    }
}
