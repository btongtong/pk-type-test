package study.idtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "auto_increment_entity")
public class AutoIncrementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int seq;

    protected AutoIncrementEntity () {}

    public AutoIncrementEntity(int seq) {
        this.seq = seq;
    }
}
