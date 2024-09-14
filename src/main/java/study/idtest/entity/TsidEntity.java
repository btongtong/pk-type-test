package study.idtest.entity;

import com.github.f4b6a3.tsid.TsidCreator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
