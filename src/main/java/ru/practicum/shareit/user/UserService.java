package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;
import java.util.Collection;


public interface UserService {
    Collection<User> getUsers();

    User addUser(User user);

    User updateUser(User user, Long userId);

    User getUser(long userId);

    User deleteUser(long userId);
}
