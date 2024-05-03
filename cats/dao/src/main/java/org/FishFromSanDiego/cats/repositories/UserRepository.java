package org.FishFromSanDiego.cats.repositories;

import org.FishFromSanDiego.cats.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
