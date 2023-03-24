package ru.nklsfnv.nklsfnvbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nklsfnv.nklsfnvbot.model.entity.Profile;
import ru.nklsfnv.nklsfnvbot.model.entity.TelegramUser;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
