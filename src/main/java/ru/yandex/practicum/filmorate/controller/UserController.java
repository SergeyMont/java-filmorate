package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
        return userStorage.findById(id);
    }

    @PostMapping("/users")
    public void create(@Validated @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: POST /users, Строка параметров запроса: '{}'",
                user.toString());
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        userStorage.create(user);
    }

    @PutMapping("/users")
    public void update(@Validated @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: PUT /users, Строка параметров запроса: '{}'",
                user.toString());
        userStorage.update(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addToFriends(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addToFriend(id,friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId){
        userService.removeFromFriends(id,friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Set<Long> allFriends(@PathVariable Long id){
        return userService.allFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<Long> findOurFriends(@PathVariable Long id, @PathVariable Long otherId){
        return userService.findOurFriends(id,otherId);
    }
}
