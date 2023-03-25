package ru.nklsfnv.nklsfnvbot.igdb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nklsfnv.nklsfnvbot.model.entity.Game;

@AllArgsConstructor
public enum IgdbApiMethod {
    GAME("/games/");

    @Getter
    private final String mapping;

}
