package study.idtest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.idtest.entity.UlidEntity;
import study.idtest.repository.UlidRepository;

@Service
public class UlidService {
    private final UlidRepository ulidRepository;

    public UlidService(UlidRepository ulidRepository) {
        this.ulidRepository = ulidRepository;
    }

    @Transactional
    public void insert(int seq) {
        UlidEntity entity = new UlidEntity(seq);
        ulidRepository.save(entity);
    }
}
