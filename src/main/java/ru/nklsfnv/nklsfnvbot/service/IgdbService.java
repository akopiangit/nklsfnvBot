package ru.nklsfnv.nklsfnvbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.nklsfnv.nklsfnvbot.igdb.GameDto;
import ru.nklsfnv.nklsfnvbot.igdb.IgdbApiMethod;
import ru.nklsfnv.nklsfnvbot.igdb.IgdbRequester;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IgdbService {

    private final IgdbRequester requester;

    private static final ParameterizedTypeReference<List<GameDto>> gameDtoType = new ParameterizedTypeReference<List<GameDto>>() {};

    public GameDto findByName(String name) {
        List<GameDto> games = requester.post(gameDtoType, IgdbApiMethod.GAME, getSearchBody(name)).getBody();
        if (games != null && games.size() > 0) {
            return games.get(0);
        } else {
            return null;
        }
    }

    private String getSearchBody(String name) {
        return String.format("search \"%s\"; fields *;", name);
    }

}
