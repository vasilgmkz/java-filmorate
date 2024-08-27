package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.repository.mappers.RatingResultSetExtractor;
import ru.yandex.practicum.filmorate.repository.mappers.RatingsResultSetExtractor;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcRatingRepository implements RatingRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final RatingResultSetExtractor ratingResultSetExtractor;
    private final RatingsResultSetExtractor ratingsResultSetExtractor;

    @Override
    public Rating getRatingId(long ratingId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("rating_id", ratingId);
        return jdbc.query("SELECT * FROM RATING WHERE rating_id = :rating_id", mapSqlParameterSource, ratingResultSetExtractor);
    }

    @Override
    public List<Rating> getRatingAll() {
        return jdbc.query("SELECT * FROM RATING", ratingsResultSetExtractor);
    }
}
