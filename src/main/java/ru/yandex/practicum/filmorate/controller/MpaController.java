package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@AllArgsConstructor
public class MpaController {
    MpaService mpaService;

    @GetMapping({"{id}"})
    public Rating getId(@PathVariable long id) {
        return mpaService.getId(id);
    }

    @GetMapping
    public List<Rating> findAll() {
        return mpaService.getRatingAll();
    }
}
