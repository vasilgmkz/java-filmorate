//package ru.yandex.practicum.filmorate.repository;
//
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.context.annotation.Import;
//import ru.yandex.practicum.filmorate.model.Film;
//import ru.yandex.practicum.filmorate.model.Genre;
//import ru.yandex.practicum.filmorate.model.Rating;
//
//import java.time.LocalDate;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@JdbcTest
//@Import(JdbcFilmRepository.class)
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@DisplayName("JdbcFilmRepository")
//class JdbcFilmRepositoryTest {
//    public static final long TEST_FILM_ID = 1L;
//    private final JdbcFilmRepository jdbcFilmRepository;
//
//    static Film getTestFilm() {
//        Film film = new Film();
//        Genre genre = new Genre();
//        genre.setId(1);
//        genre.setName("Боевик");
//        Rating rating = new Rating();
//        rating.setId(1);
//        rating.setName("G");
//        film.setId(TEST_FILM_ID);
//        film.setName("name");
//        film.setDescription("description");
//        film.setDuration(200);
//        film.setReleaseDate(LocalDate.of(2022, 4, 12));
//        film.getGenres().add(genre);
//        film.setMpa(rating);
//        return film;
//    }
//
//    @Test
//    @DisplayName("должен находить фильм по ид")
//    public void should_return_film_when_find_by_id() {
//        Film film = jdbcFilmRepository.getFilmId(TEST_FILM_ID);
//        assertThat(film).usingRecursiveComparison().isEqualTo(getTestFilm());
//    }
//}
