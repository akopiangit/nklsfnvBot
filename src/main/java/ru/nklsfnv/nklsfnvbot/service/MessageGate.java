package ru.nklsfnv.nklsfnvbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nklsfnv.nklsfnvbot.service.session.SessionState;
import ru.nklsfnv.nklsfnvbot.service.session.TelegramSessionService;
import ru.nklsfnv.nklsfnvbot.utils.AppUtils;
import ru.nklsfnv.nklsfnvbot.utils.KeyBoardCallback;
import ru.nklsfnv.nklsfnvbot.utils.TelegramKeyboardUtils;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class MessageGate {

    private final TelegramUserService telegramUserService;
    private final TelegramSessionService telegramSessionService;
    private final TelegramFeatureService telegramFeatureService;

    public SendMessage resolveMessage(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getMessage().getChatId();
        KeyBoardCallback keyBoardCallback = KeyBoardCallback.byData(callbackQuery.getData());
        String text;
        switch (keyBoardCallback) {
            case FIND_GAME -> {
                text = "Введите название игры, которую ищете";
                telegramSessionService.update(chatId, SessionState.FIND_GAME);
                return SendMessage.builder()
                        .chatId(chatId.toString())
                        .text(text)
                        .build();
            }
            case FIND_SIMILAR_GAMES -> {
                telegramSessionService.update(chatId, SessionState.FIND_SIMILAR);
                return telegramFeatureService.returnKeyboardWithSimilarGames(chatId);
            }
            case CANCEL -> {
                text = "Что нибудь еще?";
                telegramSessionService.update(chatId, SessionState.NONE);
                return SendMessage.builder()
                        .chatId(chatId.toString())
                        .text(text)
                        .build();
            }
            case FIND_BY_ID -> {
                telegramSessionService.update(chatId, SessionState.NONE);
                return telegramFeatureService.findGameByIdFromCallbackData(callbackQuery.getData(), chatId);
            }
            default -> throw new IllegalArgumentException("Неизвестный колбэк");
        }

    }

    public SendMessage resolveMessage(Message inMessage) {
        Long chatId = inMessage.getChatId();
        telegramUserService.saveUserFromTelegram(
                AppUtils.getFullName(inMessage.getChat().getFirstName(), inMessage.getChat().getLastName()),
                inMessage.getChat().getUserName()
        );
        SessionState sessionState = telegramSessionService.findOrCreateSession(chatId).getSessionState();
        if (sessionState == SessionState.FIND_GAME) {
            return resolveFeature(inMessage, sessionState, chatId);
        }
        return switch (inMessage.getText()) {
            case "/start" -> SendMessage.builder()
                    .chatId(chatId.toString())
                    .text("Привет, " + AppUtils.getFullName(
                            inMessage.getChat().getFirstName(),
                            inMessage.getChat().getLastName()) + ". Выбери что нибудь на клавиатуре.")
                    .replyMarkup(TelegramKeyboardUtils.getInlineKeyboardWithCallbacks(KeyBoardCallback.FIND_GAME))
                    .build();
            default -> SendMessage.builder()
                    .chatId(chatId.toString())
                    .text("Выбери что нибудь на клавиатуре")
                    .replyMarkup(TelegramKeyboardUtils.getInlineKeyboardWithCallbacks(KeyBoardCallback.FIND_GAME))
                    .build();
        };
    }

    private SendMessage resolveFeature(Message inMessage, SessionState sessionState, Long chatId) {
        telegramSessionService.update(chatId, SessionState.NONE);
        return switch (sessionState) {
            case FIND_GAME -> telegramFeatureService.findGameByName(inMessage.getText(), inMessage.getChatId());
            default -> throw new IllegalArgumentException("Неизвестный статус сессии");
        };
    }

}
