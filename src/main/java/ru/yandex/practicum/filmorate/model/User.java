package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class User {
    long id;
    @Email
    @NotBlank
    String email;
    @NotBlank
    String login;
    String name;
    @Past
    LocalDate birthday;
}
