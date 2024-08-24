package ru.yandex.practicum.filmorate.repository;
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
