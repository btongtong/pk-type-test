package study.idtest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.idtest.entity.AutoIncrementEntity;
import study.idtest.repository.AutoIncrementRepository;

import java.util.List;

@Service
public class AutoIncrementService {
    private final AutoIncrementRepository autoIncrementRepository;

    public AutoIncrementService(AutoIncrementRepository autoIncrementRepository) {
        this.autoIncrementRepository = autoIncrementRepository;
    }

    @Transactional
    public Long insert(int seq) {
        AutoIncrementEntity entity = new AutoIncrementEntity(seq);
        entity = autoIncrementRepository.save(entity);
        return entity.getId();
    }

    @Transactional
    public List<Long> selectRandomIds(int count) {
        return autoIncrementRepository.findRandomIds(count);
    }

    @Transactional
    public AutoIncrementEntity select(Long id) {
        return autoIncrementRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
