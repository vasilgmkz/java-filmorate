package ru.yandex.practicum.filmorate.repository.mappers;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RatingsResultSetExtractor implements ResultSetExtractor<List<Rating>> {
    @Override
    public List<Rating> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Rating> ratings = new ArrayList<>();
        while (rs.next()) {
            Rating rating = new Rating();
            rating.setId(rs.getLong("rating_id"));
            rating.setName(rs.getString("rating_name"));
            ratings.add(rating);
        }
        return ratings;
    }
}
