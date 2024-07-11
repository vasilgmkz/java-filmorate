package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;

@Data
public class User {
    long id;
    @Email
    String email;
    String login;
    String name;
    LocalDate birthday;
}
