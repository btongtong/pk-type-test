package study.idtest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.idtest.entity.TsidEntity;
import study.idtest.repository.TsidRepository;

import java.util.List;

@Service
public class TsidService {
    private final TsidRepository tsidRepository;

    public TsidService (TsidRepository tsidRepository) {
        this.tsidRepository = tsidRepository;
    }

    @Transactional
    public Long insert(int seq) {
        TsidEntity entity = new TsidEntity(seq);
        entity = tsidRepository.save(entity);
        return entity.getId();
    }

    @Transactional
    public List<Long> selectRandomIds(int count) {
        return tsidRepository.findRandomIds(count);
    }

    @Transactional
    public TsidEntity select (Long id) {
        return tsidRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
