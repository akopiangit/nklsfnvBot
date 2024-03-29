package ru.nklsfnv.nklsfnvbot.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;


@Table(schema = "IGROPOISK", name = "PROFILE")
@Entity
@Data
@ToString
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILE_ID_SEQUENCE")
    @SequenceGenerator(name = "PROFILE_ID_SEQUENCE", sequenceName = "PROFILE_ID_SEQUENCE", allocationSize = 1, schema = "IGROPOISK")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "MODIFIED_DATE")
    private LocalDateTime modifiedDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }


}
