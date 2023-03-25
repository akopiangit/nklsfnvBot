package ru.nklsfnv.nklsfnvbot.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(schema = "IGROPOISK", name = "SCORE")
@Entity
@Data
@ToString
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCORE_ID_SEQUENCE")
    @SequenceGenerator(name = "SCORE_ID_SEQUENCE", sequenceName = "SCORE_ID_SEQUENCE", allocationSize = 1, schema = "IGROPOISK")
    private Long id;

    @OneToOne
    @JoinColumn(name = "PROFILE_ID")
    private Profile profile;

    @OneToOne
    @JoinColumn(name = "GAME_ID")
    private Game game;

    @Column(name = "SCORE")
    private Double score;

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
