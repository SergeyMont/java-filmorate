package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private final FilmService filmService;
    private final FilmStorage filmStorage;

    @Autowired
    public FilmController(FilmService filmService, FilmStorage filmStorage) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        log.info("Получен запрос к эндпоинту: GET /films");
        return filmStorage.getAll();
    }

    @GetMapping("/films/{id}")
    public Film findFilmById(@PathVariable Long id){
        return filmStorage.findById(id);
    }

    @PostMapping("/films")
    public void create(@Validated @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: POST /films, Строка параметров запроса: '{}'",
                film.toString());
        validateFilm(film);
        filmStorage.create(film);
    }

    @PutMapping("/films")
    public void update(@Validated @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: PUT /films, Строка параметров запроса: '{}'",
                film.toString());
        validateFilm(film);
        filmStorage.update(film);
    }

    private void validateFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Era of films starts 28Dec1895 ");
        }
        if (film.getDuration().isNegative()) {
            throw new ValidationException("Duration of film can't be negative");
        }
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId){
        filmService.addLike(id,userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public  void deleteLike(@PathVariable Long id, @PathVariable Long userId){
        filmService.removeLike(id,userId);
    }

    @GetMapping("/films/popular")
    public  List<Film> findTopFilms(@RequestParam(value = "count",defaultValue = "10",required = false) int count){
        return filmService.findTopFilms(count);
    }

}
