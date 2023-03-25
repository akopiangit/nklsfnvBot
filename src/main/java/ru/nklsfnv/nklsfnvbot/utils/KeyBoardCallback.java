package ru.nklsfnv.nklsfnvbot.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum KeyBoardCallback {
    FIND_GAME("Найти по названию", "/findGameByName"),
    FIND_SIMILAR("Найти похожую игру", "/findSimilarGamesByName");

    @Getter
    private final String text;
    @Getter
    private final String data;

    public static KeyBoardCallback byData(String data) {
        return Arrays.stream(values()).filter(val -> val.getData().equals(data)).findFirst().orElse(null);
    }
}
