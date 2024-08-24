package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.JdbcUserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JdbcUserRepository jdbcUserRepository;

    public List<User> findAllUser() {
        return jdbcUserRepository.findAllUser();
    }

    public User save(User user) {
        return jdbcUserRepository.save(user);
    }

    public User update(User user) {
        return jdbcUserRepository.update(user);
    }

    public User getId(long id) {
        User user = jdbcUserRepository.getUserId(id);
        if (user.getId() == 0) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        return user;
    }

    public void addInFriend(long id, long friendId) {
        User user = jdbcUserRepository.getUserId(id);
        if (user.getId() == 0) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        User friendUser = jdbcUserRepository.getUserId(friendId);
        if (friendUser.getId() == 0) {
            throw new NotFoundException("Пользователь с id " + friendId + " не найден");
        }
        jdbcUserRepository.addInFriend(id, friendId);
    }

    public void deleteFromFriend(long id, long friendId) {
        User user = jdbcUserRepository.getUserId(id);
        if (user.getId() == 0) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        User friendUser = jdbcUserRepository.getUserId(friendId);
        if (friendUser.getId() == 0) {
            throw new NotFoundException("Пользователь с id " + friendId + " не найден");
        }
        jdbcUserRepository.deleteFromFriend(id, friendId);
    }

    public List<User> getFriendsUser(long id) {
        User user = jdbcUserRepository.getUserId(id);
        if (user.getId() == 0) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        return jdbcUserRepository.getFriendsUser(id);
    }

    public List<User> getCommonId(long id, long friendId) {
        User user = jdbcUserRepository.getUserId(id);
        if (user.getId() == 0) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        User friendUser = jdbcUserRepository.getUserId(friendId);
        if (friendUser.getId() == 0) {
            throw new NotFoundException("Пользователь с id " + friendId + " не найден");
        }
        return jdbcUserRepository.getCommonId(id, friendId);
    }
}
