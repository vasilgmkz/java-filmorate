package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserStorage {
    public Collection<User> findAll();
    public User create(User user);
    public User update(User user);
    public Optional<User> getId(long id);
    public void addInFriend(long id, long friendId);
    public void deleteFromFriend(long id, long friendId);
    public List<User> getFriendsUser(long id);
    public List<User> getCommonId(long id, long friendId);
}
