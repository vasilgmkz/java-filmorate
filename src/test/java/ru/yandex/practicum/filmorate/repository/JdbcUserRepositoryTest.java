package ru.yandex.practicum.filmorate.repository;

//@JdbcTest
//@Import(JdbcUserRepository.class)
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
//@DisplayName("JdbcUserRepository")
//class JdbcUserRepositoryTest {
//    public static final long TEST_USER_ID = 1L;
//    private final JdbcUserRepository jdbcUserRepository;
//
//
//    static User getTestUser() {
//        User user = new User();
//        user.setId(TEST_USER_ID);
//        user.setEmail("email@email.com");
//        user.setName("test");
//        user.setLogin("user");
//        user.setBirthday(LocalDate.of(2000, 3, 22));
//        return user;
//    }
//
//    @Test
//    @DisplayName("должен находить пользователя по ид")
//    public void should_return_user_when_find_by_id() {
//        User user = jdbcUserRepository.getUserId(TEST_USER_ID);
//        assertThat(user).usingRecursiveComparison().isEqualTo(getTestUser());
//    }
//}