package study.idtest.entity;

import com.github.f4b6a3.tsid.TsidCreator;
import jakarta.persistence.*;

@Entity
@Table(name = "tsid_entity")
public class TsidEntity {
    @Id
    private Long id;

    private int seq;

    protected TsidEntity () {}

    public TsidEntity(int seq) {
        this.id = TsidCreator.getTsid().toLong();
        this.seq = seq;
    }

    public Long getId() {
        return id;
    }
}
