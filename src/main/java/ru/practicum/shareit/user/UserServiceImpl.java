package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import java.util.Collection;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final InMemoryUserStorage inMemoryUserStorage;
    @Override
    public Collection<User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    @Override
    public User addUser(User user) {
        return inMemoryUserStorage.addUser(user);
    }

    @Override
    public User updateUser(User user, Long userId) {
        return inMemoryUserStorage.updateUser(user, userId);
    }

    @Override
    public User getUser(long userId) {
        return inMemoryUserStorage.getUser(userId);
    }

    @Override
    public User deleteUser(long userId) {
        return inMemoryUserStorage.deleteUser(userId);
    }
}
