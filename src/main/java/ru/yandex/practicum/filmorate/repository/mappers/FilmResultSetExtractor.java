package ru.yandex.practicum.filmorate.repository.mappers;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

@Component
public class FilmResultSetExtractor implements ResultSetExtractor<Film> {
    @Override
    public Film extractData(ResultSet rs) throws SQLException, DataAccessException {
        Film film = null;
        while (rs.next()) {
            if (film == null) {
                film = new Film();
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
            }
            Genre genre = new Genre();
            genre.setId(rs.getLong("genre_id"));
            genre.setName(rs.getString("genre_name"));
            if (genre.getId() == 0) {
                return film;
            }
            film.getGenres().add(genre);
        }
        return film;
    }
}