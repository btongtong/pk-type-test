package study.idtest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.idtest.entity.UuidEntity;
import study.idtest.repository.UuidRepository;

@Service
public class UuidService {
    private final UuidRepository uuidRepository;

    public UuidService(UuidRepository uuidRepository) {
        this.uuidRepository = uuidRepository;
    }

    @Transactional
    public String insert(int seq) {
        UuidEntity entity = new UuidEntity(seq);
        entity = uuidRepository.save(entity);
        return entity.getId();
    }

    @Transactional
    public UuidEntity select (String id) {
        return uuidRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
