package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.repository.mappers.FilmResultSetExtractor;
import ru.yandex.practicum.filmorate.repository.mappers.FilmsResultSetExtractor;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class JdbcFilmRepository implements FilmRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final JdbcRatingRepository jdbcRatingRepository;
    private final JdbcGenreRepository jdbcGenreRepository;
    private final FilmResultSetExtractor filmResultSetExtractor;
    private final FilmsResultSetExtractor filmsResultSetExtractor;

    @Override
    public Film save(Film film) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        List<Long> genreId = List.of();
        Set<Genre> genres;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", film.getName());
        mapSqlParameterSource.addValue("description", film.getDescription());
        mapSqlParameterSource.addValue("releaseDate", java.sql.Date.valueOf(film.getReleaseDate()));
        mapSqlParameterSource.addValue("duration", film.getDuration());
        mapSqlParameterSource.addValue("rating_id", film.getMpa().getId());
        Rating rating = jdbcRatingRepository.getRatingId(film.getMpa().getId());
        if (rating.getId() == 0) {
            throw new ValidationException("Рейтинг с id " + film.getMpa().getId() + " не найден");
        }
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            genreId = film.getGenres().stream().map(Genre::getId).toList();
            genres = jdbcGenreRepository.getGenresById(genreId);
            film.setGenres((LinkedHashSet<Genre>) genres);
        } else {
            film.setGenres(new LinkedHashSet<Genre>());
        }
        jdbc.update("INSERT INTO FILMS (name, description, releaseDate, duration, rating_id) VALUES (:name, :description, :releaseDate, :duration, :rating_id)", mapSqlParameterSource, keyHolder, new String[]{"film_id"});
        Integer film_id = keyHolder.getKeyAs(Integer.class);
        mapSqlParameterSource.addValue("film_id", film_id);
        if (!genreId.isEmpty()) {
            for (long genre_id : genreId) {
                mapSqlParameterSource.addValue("genre_id", genre_id);
                jdbc.update("INSERT INTO FilmsGenres (film_id, genre_id) VALUES (:film_id, :genre_id)", mapSqlParameterSource, keyHolder);
            }
        }
        film.setId(film_id);
        film.getMpa().setName(rating.getName());
        return film;
    }

    @Override
    public Film getFilmId(long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("film_id", id);
        String query = "SELECT * FROM FILMS AS f\n" +
                "JOIN RATING AS r ON f.RATING_ID = r.RATING_ID \n" +
                "LEFT JOIN FILMSGENRES AS fg ON f.FILM_ID = fg.FILM_ID \n" +
                "LEFT JOIN GENRES  AS g ON fg.GENRE_ID = g.GENRE_ID \n" +
                "WHERE f.FILM_ID = :film_id";
        return jdbc.query(query, mapSqlParameterSource, filmResultSetExtractor);
    }

    @Override
    public List<Film> getFilmAll() {
        String query = "SELECT * FROM FILMS AS f\n" +
                "JOIN RATING AS r ON f.RATING_ID = r.RATING_ID \n" +
                "LEFT JOIN FILMSGENRES AS fg ON f.FILM_ID = fg.FILM_ID \n" +
                "LEFT JOIN GENRES  AS g ON fg.GENRE_ID = g.GENRE_ID";
        LinkedHashMap<Long, Film> films = jdbc.query(query, filmsResultSetExtractor);
        return new ArrayList<>(films.values());
    }

    @Override
    public Film update(Film filmUpdate) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        List<Long> genreIdUpdate = List.of();
        Set<Genre> genresUpdate;
        mapSqlParameterSource.addValue("film_id", filmUpdate.getId());
        String query = "SELECT * FROM FILMS AS f\n" +
                "JOIN RATING AS r ON f.RATING_ID = r.RATING_ID \n" +
                "LEFT JOIN FILMSGENRES AS fg ON f.FILM_ID = fg.FILM_ID \n" +
                "LEFT JOIN GENRES  AS g ON fg.GENRE_ID = g.GENRE_ID \n" +
                "WHERE f.FILM_ID = :film_id";
        Film filmSave = Optional.of(Optional.ofNullable(jdbc.query(query, mapSqlParameterSource, filmResultSetExtractor)).orElseThrow(() -> new NotFoundException("Фильм с id " + filmUpdate.getId() + " не найден"))).get();
        Rating ratingUpdate = jdbcRatingRepository.getRatingId(filmUpdate.getMpa().getId());
        if (ratingUpdate.getId() == 0) {
            throw new ValidationException("Рейтинг с id " + filmUpdate.getMpa().getId() + " не найден");
        }
        filmSave.setMpa(ratingUpdate);
        if (filmUpdate.getGenres() != null && !filmUpdate.getGenres().isEmpty()) {
            genreIdUpdate = filmUpdate.getGenres().stream().map(Genre::getId).toList();
            genresUpdate = jdbcGenreRepository.getGenresById(genreIdUpdate);
            filmSave.setGenres((LinkedHashSet<Genre>) genresUpdate);
        } else {
            filmSave.setGenres(new LinkedHashSet<Genre>());
        }
        filmSave.setName(filmUpdate.getName());
        filmSave.setDuration(filmUpdate.getDuration());
        filmSave.setDescription(filmUpdate.getDescription());
        filmSave.setReleaseDate(filmUpdate.getReleaseDate());
        mapSqlParameterSource.addValue("name", filmSave.getName());
        mapSqlParameterSource.addValue("description", filmSave.getDescription());
        mapSqlParameterSource.addValue("releaseDate", java.sql.Date.valueOf(filmSave.getReleaseDate()));
        mapSqlParameterSource.addValue("duration", filmSave.getDuration());
        mapSqlParameterSource.addValue("rating_id", filmSave.getMpa().getId());
        jdbc.update("UPDATE FILMS SET NAME =:name, description = :description, releaseDate = :releaseDate, duration = :duration, rating_id = :rating_id  WHERE FILM_ID = :film_id", mapSqlParameterSource);
        jdbc.update("DELETE FROM FILMSGENRES WHERE FILM_ID =:film_id", mapSqlParameterSource);
        if (!genreIdUpdate.isEmpty()) {
            for (long genre_id : genreIdUpdate) {
                mapSqlParameterSource.addValue("genre_id", genre_id);
                jdbc.update("INSERT INTO FILMSGENRES (film_id, genre_id) VALUES (:film_id, :genre_id)", mapSqlParameterSource);
            }
        }
        return filmSave;
    }

    @Override
    public void addLikes(long filmId, long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("film_id", filmId);
        mapSqlParameterSource.addValue("user_id", userId);
        jdbc.update("INSERT INTO LIKES (film_id, user_id)\n" +
                "SELECT :film_id, :user_id WHERE NOT EXISTS (\n" +
                "SELECT 1 FROM LIKES WHERE film_id = :film_id AND user_id = :user_id)", mapSqlParameterSource);
    }

    @Override
    public void deleteLikes(long filmId, long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("film_id", filmId);
        mapSqlParameterSource.addValue("user_id", userId);
        int rowsDeleted = jdbc.update("DELETE FROM LIKES WHERE film_id = :film_id AND user_id = :user_id", mapSqlParameterSource);
        if (rowsDeleted == 0) {
            throw new NotFoundException("Ошибка удаления. У фильма отсутствует лайк пользователя.");
        }
    }

    @Override
    public List<Film> popularFilms(long count) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("count", count);
        String query = "SELECT *\n" +
                "FROM (\n" +
                "SELECT film_id, COUNT(film_id) AS countlike\n" +
                "FROM LIKES\n" +
                "GROUP BY film_id\n" +
                "ORDER BY COUNT(film_id) DESC\n" +
                "LIMIT :count\n" +
                ") AS ft\n" +
                "JOIN FILMS AS f ON ft.film_id = f.FILM_ID\n" +
                "JOIN RATING AS r ON f.RATING_ID = r.RATING_ID\n" +
                "JOIN FILMSGENRES  AS fg ON fg.FILM_ID = f.FILM_ID\n" +
                "JOIN GENRES AS g ON fg.genre_id = g.GENRE_ID";
        LinkedHashMap<Long, Film> films = jdbc.query(query, mapSqlParameterSource, filmsResultSetExtractor);
        return new ArrayList<>(films.values());
    }
}

