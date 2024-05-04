package org.FishFromSanDiego.cats.repositories;

import org.FishFromSanDiego.cats.models.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CatsRepository extends JpaRepository<Cat, Long> {
    @Query("SELECT Cat FROM Cat WHERE :cat IN friends")
    List<Cat> findAllCatsForWhomThisIsFriend(Cat cat);

    List<Cat> findAllByOwnerId(long ownerId);

//    @Query(value = "SELECT * FROM cats WHERE id IN (SELECT cat_id FROM cats_catfriends WHERE friend_id = :id)", nativeQuery = true)
//    List<Cat> getCatsForWhomThisIsFriendById(long id);

    boolean existsByOwnerId(long ownerId);
}
