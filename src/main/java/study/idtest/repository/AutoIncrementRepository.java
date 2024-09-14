package study.idtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.idtest.entity.AutoIncrementEntity;

public interface AutoIncrementRepository extends JpaRepository<AutoIncrementEntity, Long> {
}
