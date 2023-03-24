package ru.nklsfnv.nklsfnvbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nklsfnv.nklsfnvbot.model.entity.Profile;
import ru.nklsfnv.nklsfnvbot.model.entity.TelegramUser;
import ru.nklsfnv.nklsfnvbot.repository.ProfileRepository;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional
    public Profile createByTelegramUser(TelegramUser telegramUser) {
        Profile profile = new Profile();
        profile.setName(telegramUser.getName());
        return profileRepository.save(profile);
    }

}
