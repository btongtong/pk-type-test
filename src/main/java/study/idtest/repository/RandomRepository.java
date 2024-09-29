package study.idtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.idtest.entity.RandomEntity;

import java.util.List;

public interface RandomRepository extends JpaRepository<RandomEntity, Long> {
    @Query(value = "SELECT id FROM random_entity ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Long> findRandomIds(@Param("count") int count);
}
