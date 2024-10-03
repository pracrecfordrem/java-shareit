package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.ConflictException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public class InMemoryUserStorage {
    HashMap<Long,User> users = new HashMap<>();
    HashSet<String> emails = new HashSet<>();

    public Collection<User> getUsers() {
        return users.values();
    }

    public User addUser(User user) {
        if (emails.contains(user.getEmail())) {
            throw new ConflictException("email должен быть уникальными");
        }
        if (user.getId() == null)  {
            user.setId(getId());
        }
        users.put(user.getId(), user);
        emails.add(user.getEmail());
        return user;
    }


    public User updateUser(User user, Long userId) {
        if (getUser(userId) == null) {
            throw new NotFoundException("Не найден изменяемый пользователь");
        } else if (user.getEmail() != null && !user.getEmail().equals(users.get(userId).getEmail()) && emails.contains(user.getEmail())) {
            throw new ConflictException("email должны быть уникальными");
        } else {
            if (user.getName() != null) {
                users.get(userId).setName(user.getName());
            }
            if (user.getEmail() != null) {
                if (user.getEmail().indexOf('@') == -1) {
                    throw new ValidationException("Некорректный email");
                }
                users.get(userId).setEmail(user.getEmail());
                emails.add(user.getEmail());
            }
            return users.get(userId);
        }
    }

    public User getUser(Long userId) {
        return Optional.ofNullable(users.get(userId)).orElseThrow(() -> new NotFoundException("Пользователь  не существует"));
    }

    private Long getId() {
        Long lastId = users.values()
                .stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }

    private boolean checkEmail(String email) {
        if (emails.contains(email)) {
            return false;
        } else {
            return true;
        }
    }

    public User deleteUser(Long userId) {
        User user = users.get(userId);
        if (user != null) {
            users.remove(userId);
        } else {
            throw new NotFoundException("Не найден удаляемый пользователь.");
        }
        return user;
    }
}
