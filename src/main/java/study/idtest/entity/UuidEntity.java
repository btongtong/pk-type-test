package study.idtest.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "uuid_entity")
public class UuidEntity {
    @Id
    private String id;

    private int seq;

    protected UuidEntity () {}

    public UuidEntity(int seq) {
        this.id = UUID.randomUUID().toString();
        this.seq = seq;
    }

    public String getId() {
        return id;
    }
}
