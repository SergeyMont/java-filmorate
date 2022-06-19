package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class MpaController {
    private final MpaStorage mpaStorage;

    public MpaController(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    @GetMapping
    List<MpaRating> getAllMpa() {
        return mpaStorage.getAllMpa();
    }

    @GetMapping("{id}")
    MpaRating getById(@PathVariable Integer id) {
        if (id < 0) {
            throw new ValidationException("id must be positive");
        }
        return mpaStorage.getMpaById(id);
    }
}
