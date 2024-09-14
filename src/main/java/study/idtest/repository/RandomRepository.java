package study.idtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.idtest.entity.RandomEntity;

public interface RandomRepository extends JpaRepository<RandomEntity, Long> {
}
