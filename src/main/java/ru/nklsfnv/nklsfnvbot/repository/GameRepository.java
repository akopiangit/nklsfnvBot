package ru.nklsfnv.nklsfnvbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nklsfnv.nklsfnvbot.model.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
