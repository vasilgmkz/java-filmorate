package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.repository.JdbcRatingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final JdbcRatingRepository jdbcRatingRepository;

    public Rating getId(long id) {
        Rating rating = jdbcRatingRepository.getRatingId(id);
        if (rating.getId() == 0) {
            throw new NotFoundException("Рейтинг с id " + id + " не найден");
        }
        return rating;
    }

    public List<Rating> getRatingAll() {
        return jdbcRatingRepository.getRatingAll();
    }
}
