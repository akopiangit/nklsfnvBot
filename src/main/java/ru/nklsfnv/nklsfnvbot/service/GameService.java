package ru.nklsfnv.nklsfnvbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nklsfnv.nklsfnvbot.igdb.GameDto;
import ru.nklsfnv.nklsfnvbot.repository.GameRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {

    private final GameRepository gameRepository;
    private final IgdbService igdbService;

    public GameDto findGameInIgdbByTitle(String name) {
        return igdbService.findByName(name);
    }


}
