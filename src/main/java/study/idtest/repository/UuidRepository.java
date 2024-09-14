package study.idtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.idtest.entity.UuidEntity;

public interface UuidRepository extends JpaRepository<UuidEntity, String> {
}
