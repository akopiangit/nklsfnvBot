package ru.nklsfnv.nklsfnvbot.service.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nklsfnv.nklsfnvbot.igdb.GameDto;
import ru.nklsfnv.nklsfnvbot.repository.TelegramSessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramSessionService {

    private final TelegramSessionRepository telegramSessionRepository;

    public TelegramSession findOrCreateSession(Long chatId) {
        return telegramSessionRepository.findByChatId(chatId)
                .orElseGet(() -> createSession(chatId));
    }

    public TelegramSession update(Long chatId, SessionState sessionState) {
        TelegramSession telegramSession = findOrCreateSession(chatId);
        telegramSession.setSessionState(sessionState);
        return telegramSessionRepository.save(telegramSession);
    }

    public TelegramSession setSimilarGameIds(Long chatId, Set<Long> similarGames) {
        TelegramSession telegramSession = findOrCreateSession(chatId);
        telegramSession.setSimilarGameIds(similarGames);
        return telegramSessionRepository.save(telegramSession);
    }

    public TelegramSession setCurrentGameId(Long chatId, Long currentGameId) {
        TelegramSession telegramSession = findOrCreateSession(chatId);
        telegramSession.setCurrentGameId(currentGameId);
        return telegramSessionRepository.save(telegramSession);
    }

    public TelegramSession setSimilarGames(Long chatId, List<GameDto> similarGames) {
        TelegramSession telegramSession = findOrCreateSession(chatId);
        telegramSession.setSimilarGames(similarGames);
        return telegramSessionRepository.save(telegramSession);
    }

    private TelegramSession createSession(Long chatId) {
        TelegramSession telegramSession = new TelegramSession();
        telegramSession.setId(UUID.randomUUID().toString());
        telegramSession.setChatId(chatId);
        telegramSession.setSessionState(SessionState.NONE);
        telegramSession.setStartDateTime(LocalDateTime.now());
        return telegramSessionRepository.save(telegramSession);
    }

}
