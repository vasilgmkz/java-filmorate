package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserRepository {
    public User save(User user);

    public User getUserId(long id);

    public List<User> findAllUser();

    public User update(User userUpdate);

    public void addInFriend(long id, long friendId);

    public void deleteFromFriend(long id, long friendId);

    public List<User> getFriendsUser(long id);

    public List<User> getCommonId(long id, long friendId);

}
