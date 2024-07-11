package ru.yandex.practicum.filmorate.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")

public class FilmController {
    private final static Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(FilmController.class);
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        log.setLevel(Level.INFO);
        if (film.getName().isBlank()) {
            log.warn("Название не может быть пустым");
            throw new ValidationException("Название не может быть пустым");
        }
        if (film.getDescription().length() >= 200) {
            log.warn("Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            log.warn("Продолжительность фильма должна быть положительным числом");
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Фильм успешно добавлен");
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        log.setLevel(Level.INFO);
        if (films.containsKey(film.getId())) {
            if (film.getName().isBlank()) {
                log.warn("Название не может быть пустым");
                throw new ValidationException("Название не может быть пустым");
            }
            if (film.getDescription().length() >= 200) {
                log.warn("Максимальная длина описания — 200 символов");
                throw new ValidationException("Максимальная длина описания — 200 символов");
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                log.warn("Дата релиза — не раньше 28 декабря 1895 года");
                throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
            }
            if (film.getDuration() <= 0) {
                log.warn("Продолжительность фильма должна быть положительным числом");
                throw new ValidationException("Продолжительность фильма должна быть положительным числом");
            }
            films.put(film.getId(), film);
            log.info("Фильм успешно обновлен");
            return film;
        }
        log.warn("Фильм с указанным id не найден");
        throw new ValidationException("Фильм с указанным id не найден");
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
