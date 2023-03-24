package ru.nklsfnv.nklsfnvbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nklsfnv.nklsfnvbot.model.entity.TelegramUser;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {

    TelegramUser findByUserName(String userName);

}
