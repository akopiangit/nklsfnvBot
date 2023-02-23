package ru.nklsfnv.nklsfnvbot.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Table(schema = "tg", name = "user")
@Entity
@Data
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQUENCE")
    @SequenceGenerator(name = "USER_ID_SEQUENCE", sequenceName = "USER_ID_SEQUENCE", allocationSize = 1, schema = "TG")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "USERNAME")
    private String userName;

}
