package study.idtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
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
}
