package ru.nklsfnv.nklsfnvbot.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Table(schema = "igropoisk", name = "telegram_user")
@Entity
@Data
@ToString
public class TelegramUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQUENCE")
    @SequenceGenerator(name = "USER_ID_SEQUENCE", sequenceName = "USER_ID_SEQUENCE", allocationSize = 1, schema = "IGROPOISK")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "USERNAME")
    private String userName;

    @ManyToOne
    @JoinColumn(name = "PROFILE_ID")
    private Profile profile;

}
