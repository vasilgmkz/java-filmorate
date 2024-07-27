package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Service
public class ValidateService {
    public void filmValidation(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Ошибка валидации. Дата релиза — не раньше 28 декабря 1895 года");
        }
    }
    public void userValidation(User user) {
        if ((user.getLogin().contains(" "))) {
            throw new ValidationException("Ошибка валидации. Логин не может содержать пробелы");
        }
    }
}
