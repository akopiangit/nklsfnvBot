package ru.nklsfnv.nklsfnvbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nklsfnv.nklsfnvbot.igdb.GameDto;
import ru.nklsfnv.nklsfnvbot.utils.AppUtils;
import ru.nklsfnv.nklsfnvbot.utils.KeyBoardCallback;
import ru.nklsfnv.nklsfnvbot.utils.TelegramKeyboardUtils;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class MessageGate {

    private final TelegramUserService telegramUserService;
    private final GameService gameService;

    public SendMessage resolveMessage(CallbackQuery callbackQuery) {
        String chatId = callbackQuery.getMessage().getChatId().toString();
        KeyBoardCallback keyBoardCallback = KeyBoardCallback.byData(callbackQuery.getData());
        String text;
        switch (keyBoardCallback) {
            case FIND_GAME -> text = "Введите: Я ищу {название игры}";
            case FIND_SIMILAR -> text = "Введите: Я ищу похожую на {название игры}";
            default -> throw new IllegalArgumentException("Неизвестный колбэк");
        }
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }

    public SendMessage resolveMessage(Message inMessage) {
        String chatId = inMessage.getChatId().toString();
        return switch (inMessage.getText()) {
            case "/start" -> SendMessage.builder()
                    .chatId(chatId)
                    .text("Привет, " + AppUtils.getFullName(
                            inMessage.getChat().getFirstName(),
                            inMessage.getChat().getLastName()))
                    .replyMarkup(TelegramKeyboardUtils.getInlineKeyboardOnStart(KeyBoardCallback.FIND_GAME, KeyBoardCallback.FIND_SIMILAR))
                    .build();
            default -> SendMessage.builder()
                    .chatId(chatId)
                    .text(getAnswer(inMessage))
                    .build();
        };
    }

    public String getAnswer(Message inMessage) {
        log.info(
                "user [{} {} {}] sent message : \n [{}]",
                inMessage.getChat().getFirstName(),
                inMessage.getChat().getLastName(),
                inMessage.getChat().getUserName(),
                inMessage.getText()
        );
        String text = inMessage.getText();
        telegramUserService.saveUserFromTelegram(
                AppUtils.getFullName(inMessage.getChat().getFirstName(), inMessage.getChat().getLastName()),
                inMessage.getChat().getUserName()
        );
        GameDto game = gameService.findGameInIgdbByTitle(text);
        if (game != null) {
            return String.format(
                    "Найдена такая игра: \nНазвание: %s Рейтинг: %s\nОписание: %s",
                    game.getName(),
                    game.getRating(),
                    game.getStoryline()
            );
        } else {
            return "Не могу найти такую игру =(";
        }
    }

}
