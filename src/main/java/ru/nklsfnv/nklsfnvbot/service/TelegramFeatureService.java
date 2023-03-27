package ru.nklsfnv.nklsfnvbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.nklsfnv.nklsfnvbot.igdb.GameDto;
import ru.nklsfnv.nklsfnvbot.service.session.TelegramSession;
import ru.nklsfnv.nklsfnvbot.service.session.TelegramSessionService;
import ru.nklsfnv.nklsfnvbot.utils.KeyBoardCallback;
import ru.nklsfnv.nklsfnvbot.utils.TelegramKeyboardUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.nklsfnv.nklsfnvbot.utils.AppUtils.cutMessageIfTooLong;

@Service
@RequiredArgsConstructor
public class TelegramFeatureService {

    private final GameService gameService;
    private final TelegramSessionService telegramSessionService;

    public SendMessage findGameByName(String name, Long chatId) {
        GameDto game = gameService.findGameInIgdbByTitle(name);
        return findGameByName(game, chatId);
    }

    public SendMessage findGameByIdFromCallbackData(String callBackData, Long chatId) {
        GameDto game = gameService.findGamesInIgdbByIds(Set.of(KeyBoardCallback.getId(callBackData))).stream().findFirst().orElse(null);
        return findGameByName(game, chatId);
    }

    private SendMessage findGameByName(GameDto game, Long chatId) {
        String text;
        if (game != null) {
            text = cutMessageIfTooLong(String.format(
                    "Найдена такая игра: \nНазвание: %s Рейтинг: %s\nОписание: %s",
                    game.getName(),
                    game.getTotal_rating(),
                    game.getSummary()
            ));
            telegramSessionService.setSimilarGameIds(chatId, game.getSimilar_games());
            telegramSessionService.setCurrentGameId(chatId, game.getId());
            return SendMessage.builder()
                    .chatId(chatId.toString())
                    .text(text)
                    .replyMarkup(TelegramKeyboardUtils.getInlineKeyboardWithOneButton(
                            KeyBoardCallback.FIND_SIMILAR_GAMES.getText(),
                            KeyBoardCallback.FIND_SIMILAR_GAMES.getData())
                    ).build();
        } else {
            text = "Не могу найти такую игру =(";
            return SendMessage.builder()
                    .chatId(chatId.toString())
                    .text(text)
                    .build();
        }
    }

    public SendMessage returnKeyboardWithSimilarGames(Long chatId) {
        TelegramSession telegramSession = telegramSessionService.findOrCreateSession(chatId);
        List<GameDto> games = gameService.findSimilarGamesInIgdbById(telegramSession.getCurrentGameId());
        Map<Long, String> gameMap = games.stream().collect(Collectors.toMap(GameDto::getId, GameDto::getName));
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text("Вот несколько похожих игр")
                .replyMarkup(TelegramKeyboardUtils.getVerticalInlineKeyboardWithSimilarGames(gameMap)).build();
    }

}
