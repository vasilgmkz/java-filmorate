package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@AllArgsConstructor
public class GenreController {
    GenreService genreService;

    @GetMapping({"{id}"})
    public Genre getGenreId(@PathVariable long id) {
        return genreService.getGenreId(id);
    }

    @GetMapping
    public List<Genre> getGenreAll() {
        return genreService.getGenreAll();
    }
}
