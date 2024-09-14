package study.idtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.idtest.entity.TsidEntity;

public interface TsidRepository extends JpaRepository<TsidEntity, Long> {
}
