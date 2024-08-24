package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.JdbcFilmRepository;
import ru.yandex.practicum.filmorate.repository.JdbcUserRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final JdbcFilmRepository jdbcFilmRepository;
    private final JdbcUserRepository jdbcUserRepository;


    public List<Film> getFilmAll() {
        return jdbcFilmRepository.getFilmAll();
    }

    public Film save(Film film) {
        return jdbcFilmRepository.save(film);
    }

    public Film getFilmId(long id) {
        Film film = jdbcFilmRepository.getFilmId(id);
        if (film == null) {
            throw new NotFoundException("Фильм с id " + id + " не найден");
        }
        return film;
    }

    public Film update(Film film) {
        return jdbcFilmRepository.update(film);
    }


    public void addLikes(long filmId, long userId) {
        if (jdbcFilmRepository.getFilmId(filmId) == null) {
            throw new NotFoundException("Фильм с id " + filmId + " не найден");
        }
        if (jdbcUserRepository.getUserId(userId).getId() == 0) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }
        jdbcFilmRepository.addLikes(filmId, userId);
    }

    public void deleteLikes(long filmId, long userId) {
        if (jdbcFilmRepository.getFilmId(filmId) == null) {
            throw new NotFoundException("Фильм с id " + filmId + " не найден");
        }
        if (jdbcUserRepository.getUserId(userId).getId() == 0) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }
        jdbcFilmRepository.deleteLikes(filmId, userId);
    }

    public Collection<Film> popularFilms(long count) {
        return jdbcFilmRepository.popularFilms(count);
    }
}
