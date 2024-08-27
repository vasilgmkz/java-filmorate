package ru.yandex.practicum.filmorate.repository.mappers;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenreResultSetExtractor implements ResultSetExtractor<Genre> {
    @Override
    public Genre extractData(ResultSet rs) throws SQLException, DataAccessException {
        Genre genre = new Genre();
        while (rs.next()) {
            genre.setId(rs.getLong("genre_id"));
            genre.setName(rs.getString("genre_name"));
        }
        return genre;
    }
}
