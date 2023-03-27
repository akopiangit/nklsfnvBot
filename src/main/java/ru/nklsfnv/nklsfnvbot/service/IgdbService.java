package ru.nklsfnv.nklsfnvbot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.nklsfnv.nklsfnvbot.igdb.GameDto;
import ru.nklsfnv.nklsfnvbot.igdb.IgdbApiMethod;
import ru.nklsfnv.nklsfnvbot.igdb.IgdbRequester;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IgdbService {

    private final IgdbRequester requester;

    private static final ParameterizedTypeReference<List<GameDto>> gameDtoType = new ParameterizedTypeReference<List<GameDto>>() {};

    public List<GameDto> findByName(String name) {
        List<GameDto> games = requester.post(gameDtoType, IgdbApiMethod.GAME, getSearchByName(name)).getBody();
        if (games != null && games.size() > 0) {
            return games;
        } else {
            return null;
        }
    }

    public List<GameDto> findByIds(Set<Long> ids) {
        List<GameDto> games = requester.post(gameDtoType, IgdbApiMethod.GAME, getSearchByIds(ids)).getBody();
        if (games != null && games.size() > 0) {
            return games;
        } else {
            return null;
        }
    }

    public List<GameDto> findByIdsOnlyNames(Set<Long> ids) {
        List<GameDto> games = requester.post(gameDtoType, IgdbApiMethod.GAME, getSearchByIdsOnlyNames(ids)).getBody();
        if (games != null && games.size() > 0) {
            return games;
        } else {
            return null;
        }
    }

    private String getSearchByName(String name) {
        return String.format("search \"%s\"; fields name,total_rating_count,id;limit 15;", name);
    }

    private String getSearchByIds(Set<Long> ids) {
        return String.format("fields *;where id = (%s);", ids.stream().map(Object::toString).collect(Collectors.joining(",")));
    }

    private String getSearchByIdsOnlyNames(Set<Long> ids) {
        return String.format("fields id,name;where id = (%s);", ids.stream().map(Object::toString).collect(Collectors.joining(",")));
    }

}
