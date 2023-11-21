package dev.pavelsgarklavs.netflix_clone.database.repositories;

import dev.pavelsgarklavs.netflix_clone.database.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(String email);

  default boolean existsByEmail(String email) {
    return findByEmail(email).isPresent();
  }
}
