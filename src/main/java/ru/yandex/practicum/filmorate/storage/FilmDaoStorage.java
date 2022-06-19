package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDaoStorage {

    List<Film> getAll();

    Film create(Film film);

    Film update(Film film);

    Film findById(Long id);
}
