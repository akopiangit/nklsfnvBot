package ru.nklsfnv.nklsfnvbot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.nklsfnv.nklsfnvbot.service.session.TelegramSession;

import java.util.Optional;

@Repository
public interface TelegramSessionRepository extends CrudRepository<TelegramSession, String> {

    Optional<TelegramSession> findByChatId(Long chatId);

}
