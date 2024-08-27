package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.mappers.GenreResultSetExtractor;
import ru.yandex.practicum.filmorate.repository.mappers.GenresResultSetExtractor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class JdbcGenreRepository implements GenreRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final GenreResultSetExtractor genreResultSetExtractor;
    private final GenresResultSetExtractor genresResultSetExtractor;


    @Override
    public Set<Genre> getGenresById(List<Long> genresId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        Set<Genre> genres = new LinkedHashSet<>();
        for (long genreId : genresId) {
            mapSqlParameterSource.addValue("genre_id", genreId);
            Genre genre = jdbc.query("SELECT * FROM GENRES WHERE genre_id = :genre_id", mapSqlParameterSource, genreResultSetExtractor);
            if (genre.getId() == 0) {
                throw new ValidationException("Ошибка валидации. Жанры не найдены");
            }
            genres.add(genre);
        }
        return genres;
    }

    @Override
    public Genre getGenreId(long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("genre_id", id);
        return jdbc.query("SELECT * FROM GENRES WHERE genre_id = :genre_id", mapSqlParameterSource, genreResultSetExtractor);
    }

    @Override
    public List<Genre> getGenreAll() {
        return jdbc.query("SELECT * FROM GENRES", genresResultSetExtractor);
    }
}
