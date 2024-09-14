package study.idtest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@Entity
@Table(name = "random_entity")
public class RandomEntity {
    @Id
    private Long id;

    private int seq;

    protected RandomEntity () {}

    public RandomEntity(int seq) {
        this.id = ThreadLocalRandom.current().nextLong();
        this.seq = seq;
    }
}
