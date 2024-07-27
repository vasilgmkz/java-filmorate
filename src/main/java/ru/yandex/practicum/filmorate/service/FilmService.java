package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        filmStorage.getId(film.getId()).orElseThrow(() -> new NotFoundException("Фильм с id " + film.getId() + " не найден"));
        return filmStorage.update(film);
    }

    public Film getId(long id) {
        return filmStorage.getId(id).orElseThrow(() -> new NotFoundException("Фильм с id " + id + " не найден"));
    }

    public void addLikes(long filmId, long userId) {
        userStorage.getId(userId).orElseThrow(()-> new NotFoundException("Пользователь с id " + userId + " не найден"));
        filmStorage.getId(filmId).orElseThrow(()-> new NotFoundException("Фильм с id " + filmId + " не найден"));
        filmStorage.addLikes(filmId, userId);
    }
    public void deleteLikes(long filmId, long userId) {
        userStorage.getId(userId).orElseThrow(()-> new NotFoundException("Пользователь с id " + userId + " не найден"));
        filmStorage.getId(filmId).orElseThrow(()-> new NotFoundException("Фильм с id " + filmId + " не найден"));
        filmStorage.deleteLikes(filmId, userId);
    }
    public Collection<Film> popularFilms (long count) {
        return filmStorage.popularFilms(count);
    }
}
