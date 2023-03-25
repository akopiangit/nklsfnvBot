package ru.nklsfnv.nklsfnvbot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TelegramKeyboardUtils {

    public static InlineKeyboardMarkup getInlineKeyboardOnStart(KeyBoardCallback keyBoardCallback, KeyBoardCallback keyBoardCallback1) {
        return new InlineKeyboardMarkup(
                getRowList(
                        List.of(
                                getInlineKeyboardButton(keyBoardCallback),
                                getInlineKeyboardButton(keyBoardCallback1)
                        ),
                        List.of()
                )
        );
    }

    private static InlineKeyboardButton getInlineKeyboardButton(KeyBoardCallback keyBoardCallback) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(keyBoardCallback.getText());
        inlineKeyboardButton.setCallbackData(keyBoardCallback.getData());
        return inlineKeyboardButton;
    }

    private static List<List<InlineKeyboardButton>> getRowList(
            List<InlineKeyboardButton> firstRow, List<InlineKeyboardButton> secondRow) {
        return List.of(firstRow, secondRow);
    }
}
