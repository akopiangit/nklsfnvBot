package ru.nklsfnv.nklsfnvbot.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(schema = "IGROPOISK", name = "GAME")
@Entity
@Data
@ToString
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GAME_ID_SEQUENCE")
    @SequenceGenerator(name = "GAME_ID_SEQUENCE", sequenceName = "GAME_ID_SEQUENCE", allocationSize = 1, schema = "IGROPOISK")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "IGDB_ID")
    private Long igdbId;

    @Column(name = "IMAGE_LINK")
    private String imageLink;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "IGDB_RATING")
    private Double igdbRating;

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
