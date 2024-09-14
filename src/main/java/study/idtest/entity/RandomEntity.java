package study.idtest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.concurrent.ThreadLocalRandom;


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

    public Long getId() {
        return id;
    }
}
