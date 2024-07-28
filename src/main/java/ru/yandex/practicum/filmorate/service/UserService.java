package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        userStorage.getId(user.getId()).orElseThrow(() -> new NotFoundException("Пользователь с id " + user.getId() + " не найден"));
        return userStorage.update(user);
    }

    public User getId(long id) {
        return userStorage.getId(id).orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));
    }

    public void addInFriend(long id, long friendId) {
        userStorage.getId(id).orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));
        userStorage.getId(friendId).orElseThrow(() -> new NotFoundException("Пользователь с id " + friendId + " не найден"));
        userStorage.addInFriend(id, friendId);
    }

    public void deleteFromFriend(long id, long friendId) {
        userStorage.getId(id).orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));
        userStorage.getId(friendId).orElseThrow(() -> new NotFoundException("Пользователь с id " + friendId + " не найден"));
        userStorage.deleteFromFriend(id, friendId);
    }

    public List<User> getFriendsUser(long id) {
        userStorage.getId(id).orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));
        return userStorage.getFriendsUser(id);
    }

    public List<User> getCommonId(long id, long friendId) {
        return userStorage.getCommonId(id, friendId);
    }
}
