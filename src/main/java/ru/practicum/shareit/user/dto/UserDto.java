package ru.practicum.shareit.user.dto;

import lombok.Data;

@Data
public class UserDto {

    private long id;
    private String name;
    private String email;

    public UserDto(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
