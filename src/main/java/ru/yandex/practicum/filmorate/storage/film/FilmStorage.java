package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {
    Collection<Film> findAll();
    Film create(Film film);
    Film update(Film film);
    Optional<Film> getId (long id);
    public void addLikes(long id, long friendId);
    public void deleteLikes(long filmId, long userId);
    public Collection<Film> popularFilms (long count);
}
