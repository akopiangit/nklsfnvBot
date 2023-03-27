package ru.nklsfnv.nklsfnvbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nklsfnv.nklsfnvbot.igdb.GameDto;
import ru.nklsfnv.nklsfnvbot.repository.GameRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {

    private final GameRepository gameRepository;
    private final IgdbService igdbService;

    public GameDto findGameInIgdbByTitle(String name) {
         return igdbService.findByName(name).stream()
                 .filter(game -> Objects.nonNull(game.getTotal_rating_count()))
                 .max(Comparator.comparing(GameDto::getTotal_rating_count))
                 .map(gameDto -> igdbService.findByIds(Set.of(gameDto.getId())))
                 .map(list -> list.get(0)).orElse(null);
    }

    public List<GameDto> findSimilarGamesInIgdbById(Long id) {
        return igdbService.findByIds(Set.of(id))
                .stream()
                .findAny()
                .map(game -> igdbService.findByIdsOnlyNames(game.getSimilar_games()))
                .orElse(null);
    }

    public List<GameDto> findGamesInIgdbByIds(Set<Long> ids) {
        return igdbService.findByIds(ids);
    }

}
