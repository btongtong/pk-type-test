package study.idtest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.idtest.entity.AutoIncrementEntity;
import study.idtest.repository.AutoIncrementRepository;

@Service
public class AutoIncrementService {
    private final AutoIncrementRepository autoIncrementRepository;

    public AutoIncrementService(AutoIncrementRepository autoIncrementRepository) {
        this.autoIncrementRepository = autoIncrementRepository;
    }

    @Transactional
    public void insert(int seq) {
        AutoIncrementEntity entity = new AutoIncrementEntity(seq);
        autoIncrementRepository.save(entity);
    }
}
