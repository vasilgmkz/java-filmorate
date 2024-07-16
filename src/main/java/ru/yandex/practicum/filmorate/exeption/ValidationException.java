package ru.yandex.practicum.filmorate.exeption;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    final String message;
    final String fieldName;

    public ValidationException(String message, String fieldName) {
        super(message);
        this.message = message;
        this.fieldName = fieldName;
    }
}
