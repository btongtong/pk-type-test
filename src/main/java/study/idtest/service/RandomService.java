package study.idtest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.idtest.entity.RandomEntity;
import study.idtest.repository.RandomRepository;

@Service
public class RandomService {
    private final RandomRepository randomRepository;

    public RandomService(RandomRepository randomRepository) {
        this.randomRepository = randomRepository;
    }

    @Transactional
    public void insert(int seq) {
        RandomEntity entity = new RandomEntity(seq);
        randomRepository.save(entity);
    }
}
