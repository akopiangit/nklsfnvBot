package ru.nklsfnv.nklsfnvbot.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;


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

}
