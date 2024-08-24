package ru.yandex.practicum.filmorate.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import(JdbcFilmRepository.class)
@RequiredArgsConstructor (onConstructor_ = @Autowired)
@DisplayName("JdbcFilmRepository")
class JdbcFilmRepositoryTest {

}