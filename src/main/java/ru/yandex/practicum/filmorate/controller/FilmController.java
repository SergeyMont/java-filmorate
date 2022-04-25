package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private final Map<Long, Film> map = new HashMap<>();

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        log.info("Получен запрос к эндпоинту: GET /films");
        List<Film>list=new ArrayList<>();
        list.addAll(map.values());
        return list;
    }

    @PostMapping("/films")
    public void create(@Validated @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: POST /films, Строка параметров запроса: '{}'",
                 film.toString());
        validateFilm(film);
        map.put(film.getId(), film);
    }

    @PutMapping("/films")
    public void update(@Validated @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: PUT /films, Строка параметров запроса: '{}'",
                film.toString());
        validateFilm(film);
        map.put(film.getId(), film);
    }

    private void validateFilm(Film film) {
        if(film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            throw new ValidationException("Era of films starts 28Dec1895 ");
        }
        if(film.getDuration().isNegative()){
            throw new ValidationException("Duration of film can't be negative");
        }
    }

}
