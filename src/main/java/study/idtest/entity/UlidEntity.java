package study.idtest.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ulid_entity")
public class UlidEntity {
    @Id
    private String id;

    private int seq;

    protected UlidEntity () {}

    public UlidEntity(int seq) {
        this.id = UlidCreator.getUlid().toString();
        this.seq = seq;
    }
}
