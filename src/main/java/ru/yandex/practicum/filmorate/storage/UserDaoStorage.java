package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserDaoStorage {
    List<User> getAll();

    User create(User user);

    User update(User user);

    User findById(Long id);
}
