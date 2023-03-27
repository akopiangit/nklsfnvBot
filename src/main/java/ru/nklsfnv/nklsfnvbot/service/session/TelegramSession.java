package ru.nklsfnv.nklsfnvbot.service.session;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import ru.nklsfnv.nklsfnvbot.igdb.GameDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RedisHash("telegramSession")
@Getter
@Setter
public class TelegramSession implements Serializable {

    @Id
    private String id;

    @Indexed
    private Long chatId;

    private SessionState sessionState;

    private LocalDateTime startDateTime;

    private Set<Long> similarGameIds;

    private List<GameDto> similarGames;

    private Long currentGameId;

}
