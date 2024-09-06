package ru.practicum.shareit.exceptions;

/**
 * ValidationException - исключение, которое выбрасывается при некорректных данных, введенных пользователем.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
