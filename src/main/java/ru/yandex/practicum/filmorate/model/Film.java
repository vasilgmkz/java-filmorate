package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;


/**
 * Film.
 */


@Getter
@Setter
@RequiredArgsConstructor
public class Film {
    long id;
    @NotBlank
    String name;
    @Size(min = 1, max = 200)
    String description;
    LocalDate releaseDate;
    @Positive
    long duration;
    LinkedHashSet<Genre> genres;
    @NotNull
    Rating mpa;
}
