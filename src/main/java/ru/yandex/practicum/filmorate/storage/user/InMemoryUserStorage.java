package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    Map<Long, User> users = new HashMap<>();
    Map<Long, Set<Long>> usersFriends = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User create(User user) {
        log.info("Обработка запроса на добавление пользователя");
        userEmptyName(user);
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Пользователь " + user.getName() + " успешно добавлен");
        return user;
    }

    @Override
    public User update(User user) {
        log.info("Обработка запроса на обновление пользователя");
        userEmptyName(user);
        users.put(user.getId(), user);
        log.info("Пользователь " + user.getName() + " успешно обновлен");
        return user;
    }

    @Override
    public void addInFriend(long id, long friendId) {
        log.info("Начало обработки запроса на добавление в друзья");
        Set<Long> userAddInFriend = usersFriends.computeIfAbsent(id, idUser -> new HashSet<>());
        userAddInFriend.add(friendId);
        Set<Long> friendAddInFriend = usersFriends.computeIfAbsent(friendId, idFriend -> new HashSet<>());
        friendAddInFriend.add(id);
        log.info("Конец обработки запроса на добавление в друзья");
    }

    @Override
    public void deleteFromFriend(long id, long friendId) {
        log.info("Начало обработки запроса на удаление из друзей");
        Set<Long> userAddInFriend = usersFriends.computeIfAbsent(id, idUser -> new HashSet<>());
        userAddInFriend.remove(friendId);
        Set<Long> friendAddInFriend = usersFriends.computeIfAbsent(friendId, idFriend -> new HashSet<>());
        friendAddInFriend.remove(id);
        log.info("Конец обработки запроса на удаление из друзей");
    }

    @Override
    public Optional<User> getId(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getFriendsUser(long id) {
        if (usersFriends.get(id) == null) {
            return new ArrayList<>();
        }
        return users.entrySet().stream().filter(x -> usersFriends.get(id).contains(x.getKey())).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonId(long id, long friendId) {
        if (usersFriends.get(id) == null) {
            throw new NotFoundException("У пользователя с id " + id + " нет друзей");
        }
        if (usersFriends.get(friendId) == null) {
            throw new NotFoundException("У пользователя с id " + friendId + " нет друзей");
        }
        Set<Long> set = usersFriends.get(id).stream().filter(usersFriends.get(friendId)::contains).collect(Collectors.toSet());
        return users.entrySet().stream().filter(x -> set.contains(x.getKey())).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    private void userEmptyName(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}
