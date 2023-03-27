package ru.nklsfnv.nklsfnvbot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TelegramKeyboardUtils {

    public static InlineKeyboardMarkup getInlineKeyboardWithCallbacks(KeyBoardCallback... keyBoardCallbacks) {
        return new InlineKeyboardMarkup(
                getRowList(
                        Arrays.stream(keyBoardCallbacks).map(TelegramKeyboardUtils::getInlineKeyboardButton).collect(Collectors.toList()),
                        List.of()
                )
        );
    }

    public static InlineKeyboardMarkup getInlineKeyboardWithOneButton(String text, String callBack) {
        return getInlineKeyboardFromMap(Map.of(text, callBack));
    }

    public static InlineKeyboardMarkup getInlineKeyboardFromMap(Map<String, String> buttons) {
        return new InlineKeyboardMarkup(
                        buttons.entrySet()
                                .stream().map(entry ->
                                        List.of(getInlineKeyboardButton(entry.getKey(), entry.getValue())))
                                .collect(Collectors.toList()));
    }

    public static InlineKeyboardMarkup getVerticalInlineKeyboardWithSimilarGames(Map<Long, String> games) {
        return new InlineKeyboardMarkup(
                games.entrySet()
                        .stream()
                        .map(game -> List.of(getInlineKeyboardButtonForSimilarGame(game.getKey(), game.getValue())))
                        .collect(Collectors.toList())
        );
    }

    private static InlineKeyboardButton getInlineKeyboardButtonForSimilarGame(Long gameId, String name) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setCallbackData(KeyBoardCallback.FIND_BY_ID.constructData(gameId));
        inlineKeyboardButton.setText(name);
        return inlineKeyboardButton;
    }

    private static InlineKeyboardButton getInlineKeyboardButton(KeyBoardCallback keyBoardCallback) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(keyBoardCallback.getText());
        inlineKeyboardButton.setCallbackData(keyBoardCallback.getData());
        return inlineKeyboardButton;
    }

    private static InlineKeyboardButton getInlineKeyboardButton(String text, String callBack) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(callBack);
        return inlineKeyboardButton;
    }

    @SafeVarargs
    private static List<List<InlineKeyboardButton>> getRowList(
            List<InlineKeyboardButton>... rows) {
        return List.of(rows);
    }

}