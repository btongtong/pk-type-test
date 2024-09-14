package study.idtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.idtest.entity.UlidEntity;

public interface UlidRepository extends JpaRepository<UlidEntity, String> {
}
