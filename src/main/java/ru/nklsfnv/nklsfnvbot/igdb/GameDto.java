package ru.nklsfnv.nklsfnvbot.igdb;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class GameDto {
    private Long id;
    private String name;
    private Double total_rating;
    private Integer total_rating_count;
    private String summary;
    private Set<Long> similar_games;
}
