package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.ValidateService;
import java.util.Collection;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/films")
@AllArgsConstructor
public class FilmController {

    private final FilmService filmService;
    private final ValidateService validateService;

    @GetMapping
    public Collection<Film> findAll() {
        return filmService.findAll();
    }
    @GetMapping({"{id}"})
    public Film getId(@PathVariable long id) {
        return filmService.getId(id);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        validateService.filmValidation(film);
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        validateService.filmValidation(film);
        return filmService.update(film);
    }
    @PutMapping({"{filmId}/like/{userId}"})
    public void addLikes(@PathVariable Map<String, String> pathVarsMap) {
        try {
            long filmId = Long.parseLong(pathVarsMap.get("filmId"));
            long userId = Long.parseLong(pathVarsMap.get("userId"));
            filmService.addLikes(filmId, userId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Ошибка валидации. Не верный формат filmId или UserId");
        }
    }
    @DeleteMapping({"{filmId}/like/{userId}"})
    public void deleteLikes(@PathVariable Map<String, String> pathVarsMap) {
        try {
            long filmId = Long.parseLong(pathVarsMap.get("filmId"));
            long userId = Long.parseLong(pathVarsMap.get("userId"));
            filmService.deleteLikes(filmId, userId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Ошибка валидации. Не верный формат filmId или UserId");
        }
    }
    @GetMapping({"/popular"})
    public Collection<Film> popularFilms (@RequestParam(defaultValue = "10") String count) {
        try {
            long counts = Long.parseLong(count);
            return filmService.popularFilms(counts);
        } catch (NumberFormatException e) {
            throw new ValidationException("Ошибка валидации. Не верный формат count");
        }
    }
}
