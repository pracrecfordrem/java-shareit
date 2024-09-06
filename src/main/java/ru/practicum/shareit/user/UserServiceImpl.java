package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();
    @Override
    public List<User> getUsers() {
        return (List<User>) inMemoryUserStorage.getUsers();
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
