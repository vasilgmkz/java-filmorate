package ru.yandex.practicum.filmorate.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private static final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.setLevel(Level.INFO);
        userValidation(user);
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Пользователь " + user.getName() + " успешно добавлен");
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.setLevel(Level.INFO);
        if (users.containsKey(user.getId())) {
            userValidation(user);
            users.put(user.getId(), user);
            log.info("Пользователь " + user.getName() + " успешно обновлен");
            return user;
        }
        log.warn("Пользователь с id " + user.getId() + " не найден");
        throw new ValidationException("Пользователь с id " + user.getId() + " не найден", "id");
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    private void userValidation(User user) {
        log.setLevel(Level.WARN);
        if ((user.getLogin().contains(" "))) {
            log.warn("Логин не может содержать пробелы");
            throw new ValidationException("Логин не может содержать пробелы", "login");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}
