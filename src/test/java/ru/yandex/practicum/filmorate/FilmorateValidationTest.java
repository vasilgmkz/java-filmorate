package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FilmorateValidationTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Отсутствие имени")
    public void testNotName() {
        Film film = Film.builder()
                .name(" ")
                .description("description")
                .releaseDate(LocalDate.of(2023, 12, 12))
                .duration(15)
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "значение должно быть False");
        Film film1 = Film.builder()
                .description("description")
                .releaseDate(LocalDate.of(2023, 12, 12))
                .duration(15)
                .build();
        Set<ConstraintViolation<Film>> violations1 = validator.validate(film1);
        assertFalse(violations1.isEmpty(), "значение должно быть False");
        Film film2 = Film.builder()
                .name("")
                .description("description")
                .releaseDate(LocalDate.of(2023, 12, 12))
                .duration(15)
                .build();
        Set<ConstraintViolation<Film>> violations2 = validator.validate(film2);
        assertFalse(violations2.isEmpty(), "значение должно быть False");
    }

    @Test
    @DisplayName("Превышение размера описания")
    public void testDescriptionMore() {
        Film film = Film.builder()
                .name("name")
                .description("descriptiondescriptiondescriptiondescriptiondesc" +
                        "riptiondescriptiondescriptionddescriptiondescriptiondescriptiondescriptiondescription" +
                        "descriptiondescriptiondescriptiondescriptiondescriptiondescriptionescription")
                .releaseDate(LocalDate.of(2023, 12, 12))
                .duration(15)
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "значение должно быть False");
    }

    @Test
    @DisplayName("Не положительная продолжительность")
    public void testNotPositiveDuration() {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(2023, 12, 12))
                .duration(-15)
                .build();
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "значение должно быть False");
    }
}
