package study.idtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.idtest.entity.UuidEntity;

import java.util.List;

public interface UuidRepository extends JpaRepository<UuidEntity, String> {
    @Query(value = "SELECT id FROM uuid_entity ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<String> findRandomIds(@Param("count") int count);
}
