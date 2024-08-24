package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreRepository {
    public Set<Genre> getGenresById(List<Long> genres_id);

    public Genre getGenreId(long id);

    public List<Genre> getGenreAll();
}
