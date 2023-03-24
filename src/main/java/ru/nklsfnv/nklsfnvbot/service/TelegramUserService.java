package ru.nklsfnv.nklsfnvbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nklsfnv.nklsfnvbot.model.entity.TelegramUser;
import ru.nklsfnv.nklsfnvbot.repository.TelegramUserRepository;

@RequiredArgsConstructor
@Service
public class TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;
    private final ProfileService profileService;

    public void saveUserFromTelegram(String name, String userName) {
        TelegramUser telegramUser = telegramUserRepository.findByUserName(userName);
        if (telegramUser != null) {
            return;
        }
        telegramUser = new TelegramUser();
        telegramUser.setName(name);
        telegramUser.setUserName(userName);
        telegramUser.setProfile(profileService.createByTelegramUser(telegramUser));
        telegramUserRepository.save(telegramUser);
    }

}
