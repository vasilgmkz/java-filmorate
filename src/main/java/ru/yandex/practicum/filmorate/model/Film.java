package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;


/**
 * Film.
 */


@Getter
@Setter
@AllArgsConstructor
@Builder
public class Film {
    long id;
    @NotBlank
    String name;
    @Size(min = 1, max = 200)
    String description;
    LocalDate releaseDate;
    @Positive
    long duration;
}
