package ru.yandex.practicum.filmorate.repository.mappers;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

@Component
public class FilmsResultSetExtractor implements ResultSetExtractor<LinkedHashMap<Long, Film>> {
    @Override
    public LinkedHashMap<Long, Film> extractData(ResultSet rs) throws SQLException, DataAccessException {
        LinkedHashMap<Long, Film> films = new LinkedHashMap<>();
        while (rs.next()) {
            if (!films.containsKey(rs.getLong("film_id"))) {
                Film film = new Film();
                film.setId(rs.getLong("film_id"));
                film.setName(rs.getString("name"));
                film.setDescription(rs.getString("description"));
                film.setReleaseDate(rs.getDate("releaseDate").toLocalDate());
                film.setDuration(rs.getLong("duration"));
                Rating rating = new Rating();
                rating.setId(rs.getLong("rating_id"));
                rating.setName(rs.getString("rating_name"));
                film.setMpa(rating);
                film.setGenres(new LinkedHashSet<>());
                films.put(film.getId(), film);
            }
            Genre genre = new Genre();
            genre.setId(rs.getLong("genre_id"));
            genre.setName(rs.getString("genre_name"));
            if (genre.getId() != 0) {
                films.get(rs.getLong("film_id")).getGenres().add(genre);
            }
        }
        return films;
    }
}
