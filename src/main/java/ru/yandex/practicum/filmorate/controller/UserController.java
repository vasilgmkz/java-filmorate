package ru.yandex.practicum.filmorate.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.service.ValidateService;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ValidateService validateService;

    @GetMapping
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public User getId(@PathVariable long id) {
        return userService.getId(id);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        validateService.userValidation(user);
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        validateService.userValidation(user);
        return userService.update(user);
    }

    @PutMapping({"{id}/friends/{friendId}"})
    public void addInFriend(@PathVariable Map<String, String> pathVarsMap) {
        try {
            long id = Long.parseLong(pathVarsMap.get("id"));
            long friendId = Long.parseLong(pathVarsMap.get("friendId"));
            userService.addInFriend(id, friendId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Ошибка валидации. Не верный формат userId или friendId");
        }
    }

    @DeleteMapping({"{id}/friends/{friendId}"})
    public void deleteFromFriend(@PathVariable Map<String, String> pathVarsMap) {
        try {
            long id = Long.parseLong(pathVarsMap.get("id"));
            long friendId = Long.parseLong(pathVarsMap.get("friendId"));
            userService.deleteFromFriend(id, friendId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Ошибка валидации. Не верный формат userId или friendId");
        }
    }
    @GetMapping({"{id}/friends"})
    public List<User> getFriendsUser(@PathVariable("id") String userId) {
        try {
            long id = Long.parseLong(userId);
            return userService.getFriendsUser(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Ошибка валидации. Не верный формат userId");
        }
    }
    @GetMapping({"{id}/friends/common/{otherId}"})
    public List<User> getCommonId(@PathVariable Map<String, String> pathVarsMap) {
        try {
            long id = Long.parseLong(pathVarsMap.get("id"));
            long otherId = Long.parseLong(pathVarsMap.get("otherId"));
            return userService.getCommonId(id, otherId );
        } catch (NumberFormatException e) {
            throw new ValidationException("Ошибка валидации. Не верный формат userId или otherId");
        }
    }
}
