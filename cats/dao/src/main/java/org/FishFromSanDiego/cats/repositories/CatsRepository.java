package org.FishFromSanDiego.cats.repositories;

import org.FishFromSanDiego.cats.models.Cat;
import org.FishFromSanDiego.cats.models.Colour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CatsRepository extends JpaRepository<Cat, Long> {

    List<Cat> findAllByOwnerId(long ownerId);

    List<Cat> findAllByColour(Colour colour);

    @Deprecated
    @Query(value = "SELECT * FROM cats WHERE id IN (SELECT cat_id FROM cats_catfriends WHERE friend_id = :id)", nativeQuery = true)
    List<Cat> findAllCatsForWhomThisIsFriend(long id);

    List<Cat> findAllByFriends_Id(long id);
}
