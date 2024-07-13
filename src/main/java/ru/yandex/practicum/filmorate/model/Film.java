package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {
    long id;
    @NotBlank
    String name;
    String description;
    LocalDate releaseDate;
    @PositiveOrZero
    long duration;
}
