package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import java.util.Collection;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Collection<User> getUsers() {
        return null;//inMemoryUserStorage.getUsers();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, Long userId) {
        return null;//inMemoryUserStorage.updateUser(user, userId);
    }

    @Override
    public User getUser(Long userId) {
        return null;//inMemoryUserStorage.getUser(userId);
    }

    @Override
    public User deleteUser(Long userId) {
        return null;//inMemoryUserStorage.deleteUser(userId);
    }
}
