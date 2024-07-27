package ru.yandex.practicum.filmorate.storage.film;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    Map<Long, Film> films = new HashMap<>();
    @Getter
    Map<Long, Set<Long>> usersLikeId = new HashMap<>();

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film create(Film film) {
        log.info("Обработка запроса на добавление фильма");
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Фильм " + film.getName() + " успешно добавлен");
        return film;
    }

    @Override
    public Film update(Film film) {
        log.info("Обработка запроса на обновление фильма");
        films.put(film.getId(), film);
        log.info("Фильм " + film.getName() + " успешно обновлен");
        return film;
    }

    @Override
    public Optional<Film> getId(long id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public void addLikes(long filmId, long userId) {
        log.info("Начало обработки запроса на добавление лайка");
        Set<Long> filmAddLike = usersLikeId.computeIfAbsent(filmId, idFilm -> new HashSet<>());
        filmAddLike.add(userId);
        log.info("Конец обработки запроса на добавление лайка");
        System.out.println(usersLikeId);
    }

    @Override
    public void deleteLikes(long filmId, long userId) {
        log.info("Начало обработки запроса на удаление лайка");
        Set<Long> filmAddLike = usersLikeId.computeIfAbsent(filmId, idFilm -> new HashSet<>());
        filmAddLike.remove(userId);
        log.info("Конец обработки запроса на удаление лайка");
    }

    @Override
    public Collection<Film> popularFilms(long count) {
        return usersLikeId.entrySet()
                .stream()
                .limit(count)
                .sorted(Comparator.comparing(Map.Entry::getValue, (p1, p2) -> {
                    return p2.size() - p1.size();
                }))
                .map(Map.Entry::getKey)
                .map(x -> films.get(x))
                .collect(Collectors.toList());
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
