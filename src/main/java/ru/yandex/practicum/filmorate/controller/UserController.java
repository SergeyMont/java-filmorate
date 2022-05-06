package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@RestController
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserStorage userStorage;

    @Autowired
    public UserController(UserService userService, UserStorage userStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        log.info("Получен запрос к эндпоинту: GET /users");
        return userStorage.getAll();
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable("id") Long id) {
        if (id < 0) {
            throw new ValidationException("id must be positive");
        }
        return userStorage.findById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> create(@Validated @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: POST /users, Строка параметров запроса: '{}'",
                user.toString());
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        userStorage.create(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users")
    public User update(@Validated @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: PUT /users, Строка параметров запроса: '{}'",
                user.toString());
        return userStorage.update(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addToFriends(@PathVariable Long id, @PathVariable Long friendId) {
        if (id < 0 || friendId < 0) {
            throw new ValidationException("id must be positive");
        }
        userService.addToFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.removeFromFriends(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Set<User> allFriends(@PathVariable Long id) {
        return userService.allFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<User> findOurFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.findOurFriends(id, otherId);
    }
}
