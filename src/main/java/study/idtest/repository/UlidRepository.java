package study.idtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.idtest.entity.UlidEntity;

import java.util.List;

public interface UlidRepository extends JpaRepository<UlidEntity, String> {
    @Query(value = "SELECT id FROM ulid_entity ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<String> findRandomIds(@Param("count") int count);
}
