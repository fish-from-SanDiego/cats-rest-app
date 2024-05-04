package org.FishFromSanDiego.cats.repositories;

import org.FishFromSanDiego.cats.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
}
