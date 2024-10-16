package ru.practicum.shareit.user;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
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

    @Transactional
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(User user, Long userId) {
        User updatedUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        updatedUser.setId(userId);
        if (user.getEmail() != null) {
            updatedUser.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            updatedUser.setName(user.getName());
        }
        return userRepository.save(updatedUser);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    @Override
    public User deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);
        return user;
    }
}
