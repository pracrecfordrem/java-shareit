package ru.practicum.shareit.exceptions;

/**
 * NotFoundException - исключение, которое выбрасывается, когда запрашиваемый объект не найден.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
