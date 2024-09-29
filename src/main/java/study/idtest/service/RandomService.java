package study.idtest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.idtest.entity.RandomEntity;
import study.idtest.repository.RandomRepository;

import java.util.List;

@Service
public class RandomService {
    private final RandomRepository randomRepository;

    public RandomService(RandomRepository randomRepository) {
        this.randomRepository = randomRepository;
    }

    @Transactional
    public Long insert(int seq) {
        RandomEntity entity = new RandomEntity(seq);
        entity = randomRepository.save(entity);
        return entity.getId();
    }

    @Transactional
    public List<Long> selectRandomIds(int count) {
        return randomRepository.findRandomIds(count);
    }

    @Transactional
    public RandomEntity select(Long id) {
        return randomRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
