package dev.pavelsgarklavs.netflix_clone.database.repositories;

import dev.pavelsgarklavs.netflix_clone.database.models.SubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubUserRepository extends JpaRepository<SubUser, UUID> {
    @Query(value = "SELECT t.name FROM SubUser t INNER JOIN t.user u WHERE u.id = :id")
    List<String> getAllByUserId(@Param("id") UUID id);

    @Query(value = "SELECT t FROM SubUser t INNER JOIN t.user u WHERE u.id = :userId AND t.id = :subUserId")
    Optional<SubUser> getByUserIdAndSubUserId(@Param("userId") UUID userId, @Param("subUserId") UUID subUserId);
}

