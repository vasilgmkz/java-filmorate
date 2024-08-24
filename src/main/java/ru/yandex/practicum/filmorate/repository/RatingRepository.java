package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

public interface RatingRepository {
    public Rating getRatingId(long ratingId);

    public List<Rating> getRatingAll();
}
