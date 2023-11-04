package dev.pavelsgarklavs.netflix_clone.database.repositories;

import dev.pavelsgarklavs.netflix_clone.database.models.SubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubUserRepository extends JpaRepository<SubUser, UUID> {
    @Query(value = """
      select t.name from SubUser t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id\s
      """)
    List<String> getAllByUserId(UUID id);

    @Query(value = """
      select t from SubUser t inner join User u
      on t.user.id = u.id
      where u.id = :userId and t.id = :subUserId
      """)
    Optional<SubUser> getByUserIdAndSubUserId(UUID userId, UUID subUserId);
}
