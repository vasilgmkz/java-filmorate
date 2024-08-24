package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exeption.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.mappers.UserResultSetExtractor;
import ru.yandex.practicum.filmorate.repository.mappers.UsersResultSetExtractor;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final UserResultSetExtractor userResultSetExtractor;
    private final UsersResultSetExtractor usersResultSetExtractor;

    @Override
    public User save(User user) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        mapSqlParameterSource.addValue("user_name", user.getName());
        mapSqlParameterSource.addValue("login", user.getLogin());
        mapSqlParameterSource.addValue("email", user.getEmail());
        mapSqlParameterSource.addValue("birthday", java.sql.Date.valueOf(user.getBirthday()));
        jdbc.update("INSERT INTO Users (email, login, user_name, birthday) VALUES (:email, :login, :user_name, :birthday)", mapSqlParameterSource, keyHolder, new String[]{"user_id"});
        Integer user_id = keyHolder.getKeyAs(Integer.class);
        user.setId(user_id);
        return user;
    }

    @Override
    public User getUserId(long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", id);
        String query = "SELECT * FROM USERS WHERE user_id =:user_id";
        return jdbc.query(query, mapSqlParameterSource, userResultSetExtractor);
    }

    @Override
    public List<User> findAllUser() {
        String query = "SELECT * FROM USERS";
        return jdbc.query(query, usersResultSetExtractor);
    }

    @Override
    public User update(User userUpdate) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        User userSave = getUserId(userUpdate.getId());
        if (userSave.getId() == 0) {
            throw new NotFoundException("Пользователь с id " + userUpdate.getId() + " не найден");
        }
        if (userUpdate.getName() == null || userUpdate.getName().isEmpty()) {
            userUpdate.setName(userUpdate.getLogin());
        }
        mapSqlParameterSource.addValue("user_name", userUpdate.getName());
        mapSqlParameterSource.addValue("login", userUpdate.getLogin());
        mapSqlParameterSource.addValue("email", userUpdate.getEmail());
        mapSqlParameterSource.addValue("birthday", java.sql.Date.valueOf(userUpdate.getBirthday()));
        mapSqlParameterSource.addValue("user_id", userUpdate.getId());
        jdbc.update("UPDATE USERS SET user_name = :user_name, login = :login, email = :email, birthday = :birthday where user_id = :user_id", mapSqlParameterSource);
        return userUpdate;
    }

    @Override
    public void addInFriend(long id, long friendId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", id);
        mapSqlParameterSource.addValue("friend_id", friendId);
        jdbc.update("INSERT INTO FRIENDS (user_id, friend_id) " +
                "SELECT :user_id, :friend_id WHERE NOT EXISTS " +
                "(SELECT 1 FROM FRIENDS WHERE user_id = :user_id AND friend_id = :friend_id)", mapSqlParameterSource);
//        jdbc.update("INSERT INTO FRIENDS (user_id, friend_id)" +
//                        "SELECT :friend_id, :user_id WHERE NOT EXISTS" +
//                        "(SELECT 1 FROM FRIENDS WHERE user_id = :friend_id AND friend_id = :user_id)", mapSqlParameterSource);
    }

    @Override
    public void deleteFromFriend(long id, long friendId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("user_id", id);
        mapSqlParameterSource.addValue("friend_id", friendId);
        //int rowsDeleted = jdbc.update("DELETE FROM FRIENDS WHERE user_id = :user_id AND friend_id = :friend_id OR user_id = :friend_id AND friend_id = :user_id", mapSqlParameterSource);
        int rowsDeleted = jdbc.update("DELETE FROM FRIENDS WHERE user_id = :user_id AND friend_id = :friend_id", mapSqlParameterSource);
//        if (rowsDeleted == 0) {
//            throw new NotFoundException("Ошибка удаления. Пользователи не являются друзьями.");
//        }
    }

    @Override
    public List<User> getFriendsUser(long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        String query = "SELECT u.*\n" +
                "FROM FRIENDS AS f\n" +
                "JOIN USERS AS u ON f.FRIEND_ID = u.USER_ID\n" +
                "WHERE f.USER_ID = :id\n" +
                "ORDER BY u.USER_ID";
        return jdbc.query(query, mapSqlParameterSource, usersResultSetExtractor);
    }

    @Override
    public List<User> getCommonId(long id, long friendId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        mapSqlParameterSource.addValue("friendId", friendId);
        String query = "SELECT *\n" +
                "FROM USERS u \n" +
                "WHERE u.USER_ID IN (\n" +
                "SELECT f.friend_id\n" +
                "FROM FRIENDS AS f\n" +
                "WHERE f.FRIEND_ID IN (SELECT f.FRIEND_ID \n" +
                "FROM FRIENDS AS f\n" +
                "WHERE f.USER_ID = :id) AND f.USER_ID =:friendId\n" +
                "ORDER BY f.FRIEND_ID)";
        return jdbc.query(query, mapSqlParameterSource, usersResultSetExtractor);
    }
}

