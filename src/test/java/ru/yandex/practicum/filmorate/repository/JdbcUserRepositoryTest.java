package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@Import(JdbcUserRepository.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("JdbcUserRepository")
class JdbcUserRepositoryTest {
    public static final long TEST_USER_ID = 1L;
    private final JdbcUserRepository jdbcUserRepository;


    static User getTestUser() {
        User user = new User();
        user.setId(TEST_USER_ID);
        user.setEmail("email@email.com");
        user.setName("test");
        user.setLogin("user");
        user.setBirthday(LocalDate.of(2000, 3, 22));
        return user;
    }

    @Test
    @DisplayName("должен находить пользователя по ид")
    public void should_return_user_when_find_by_id() {
        User user = jdbcUserRepository.getUserId(TEST_USER_ID);
        assertThat(user).usingRecursiveComparison().isEqualTo(getTestUser());
    }
}