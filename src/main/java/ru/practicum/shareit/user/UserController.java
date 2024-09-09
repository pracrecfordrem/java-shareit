package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {
    UserService userService;

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public User addUser(@Validated @RequestBody User user) {
        return userService.addUser(user);
    }

    @PatchMapping("/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable("userId") long userId) {
        return userService.updateUser(user, userId);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") long userId) {
        return userService.getUser(userId);
    }

    @DeleteMapping("/{userId}")
    public User deleteUser(@PathVariable("userId") long userId) {
        return userService.deleteUser(userId);
    }
}
