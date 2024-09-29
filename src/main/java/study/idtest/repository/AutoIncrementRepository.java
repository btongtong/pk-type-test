package study.idtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.idtest.entity.AutoIncrementEntity;

import java.util.List;

public interface AutoIncrementRepository extends JpaRepository<AutoIncrementEntity, Long> {
    @Query(value = "SELECT id FROM auto_increment_entity ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Long> findRandomIds(@Param("count") int count);
}
