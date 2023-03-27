package ru.nklsfnv.nklsfnvbot.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum KeyBoardCallback {
    FIND_GAME("Найти по названию", "/findGameByName"),
    FIND_SIMILAR_GAMES("Найти похожие", "/findSimilarGames"),
    FIND_BY_ID(null, "/findById") {
        @Override
        public String constructData(Long id) {
            return getData() + " " + id;
        }
    },
    CANCEL("Отмена", "/cancel");

    @Getter
    private final String text;
    @Getter
    private final String data;

    public static KeyBoardCallback byData(String callbackData) {
        String data = callbackData.split(" ")[0];
        return Arrays.stream(values()).filter(val -> val.getData().equals(data)).findFirst().orElse(null);
    }

    public String constructData(Long id) {
       return data;
    }

    public static Long getId(String callBackData) {
        return Long.parseLong(callBackData.split(" ")[1]);
    }
}
