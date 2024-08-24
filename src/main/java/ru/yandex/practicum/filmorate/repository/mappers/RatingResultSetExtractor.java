package ru.yandex.practicum.filmorate.repository.mappers;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RatingResultSetExtractor implements ResultSetExtractor<Rating> {
    @Override
    public Rating extractData(ResultSet rs) throws SQLException, DataAccessException {
        Rating rating = new Rating();
        while (rs.next()) {
            rating.setId(rs.getLong("rating_id"));
            rating.setName(rs.getString("rating_name"));
        }
        return rating;
    }
}
