package dev.pavelsgarklavs.netflix_clone.database.repositories;

import dev.pavelsgarklavs.netflix_clone.database.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

  @Query(value = "SELECT t FROM Token t INNER JOIN t.user u " +
          "WHERE u.id = :id AND (t.expired = false OR t.revoked = false)")
  List<Token> findAllValidTokenByUser(@Param("id") UUID id);

  Optional<Token> findByToken(String token);
}
