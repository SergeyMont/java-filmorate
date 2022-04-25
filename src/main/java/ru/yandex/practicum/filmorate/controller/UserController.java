package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private final Map<Long, User> map = new HashMap<>();

    @GetMapping("/users")
    public List<User> findAllUsers() {
        log.info("Получен запрос к эндпоинту: GET /users");
        List<User> list = new ArrayList<>();
        list.addAll(map.values());
        return list;
    }

    @PostMapping("/users")
    public void create(@Validated @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: POST /users, Строка параметров запроса: '{}'",
                user.toString());
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        map.put(user.getId(), user);
    }

    @PutMapping("/users")
    public void update(@Validated @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: PUT /users, Строка параметров запроса: '{}'",
                user.toString());
        map.put(user.getId(), user);
    }
}
