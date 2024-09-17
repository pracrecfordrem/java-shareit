package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    @Email(message = "Email не корректно передан")
    @NotNull(message = "Email не должен быть пустым")
    private String email;
}
