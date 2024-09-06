package ru.practicum.shareit.exceptions;

/**
 * InternalServerException - исключение, обозначающее внутреннюю ошибку сервера.
 */
public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }
}
