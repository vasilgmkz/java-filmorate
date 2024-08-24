package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.repository.JdbcGenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final JdbcGenreRepository jdbcGenreRepository;

    public Genre getGenreId(long id) {
        Genre genre = jdbcGenreRepository.getGenreId(id);
        if (genre.getId() == 0) {
            throw new NotFoundException("Жанр с id " + id + " не найден");
        }
        return genre;
    }

    public List<Genre> getGenreAll() {
        return jdbcGenreRepository.getGenreAll();
    }
}
