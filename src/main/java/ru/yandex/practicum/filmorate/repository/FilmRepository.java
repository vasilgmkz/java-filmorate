package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmRepository {
    public Film save(Film film);

    public Film getFilmId(long id);

    public List<Film> getFilmAll();

    public Film update(Film film);

    public void addLikes(long filmId, long userId);

    public void deleteLikes(long filmId, long userId);

    public List<Film> popularFilms(long count);
}
