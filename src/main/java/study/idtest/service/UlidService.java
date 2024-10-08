package study.idtest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.idtest.entity.UlidEntity;
import study.idtest.repository.UlidRepository;

import java.util.List;

@Service
public class UlidService {
    private final UlidRepository ulidRepository;

    public UlidService(UlidRepository ulidRepository) {
        this.ulidRepository = ulidRepository;
    }

    @Transactional
    public String insert(int seq) {
        UlidEntity entity = new UlidEntity(seq);
        entity = ulidRepository.save(entity);
        return entity.getId();
    }

    @Transactional
    public List<String> selectRandomIds(int count) {
        return ulidRepository.findRandomIds(count);
    }

    @Transactional
    public UlidEntity select(String id) {
        return ulidRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
