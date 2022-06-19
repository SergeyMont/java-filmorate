package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmDateValidationException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmDaoStorage;


import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class FilmController {
    private final FilmService filmService;

    private static final Date ERAFILM = Date.valueOf(LocalDate.of(1895, 12, 28));

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        log.info("Получен запрос к эндпоинту: GET /films");
        return filmService.findAll();
    }

    @GetMapping("/films/{id}")
    public Film findFilmById(@PathVariable Long id) {
        if (id < 0) {
            throw new ValidationException("id must be positive");
        }
        Film film = filmService.findById(id);
        return film;
    }

    @PostMapping("/films")
    public Film create(@Validated @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: POST /films, Строка параметров запроса: '{}'",
                film.toString());
        validateFilm(film);
        film = filmService.createFilm(film);
        return film;
    }

    @PutMapping("/films")
    public Film update(@Validated @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: PUT /films, Строка параметров запроса: '{}'",
                film.toString());
        if (film.getId() < 0) {
            throw new ValidationException("id must be positive");
        }
        validateFilm(film);
        Film result = filmService.updateFilm(film);
        if (result.getGenres() != null) {
            result.setGenres(result.getGenres().stream().sorted(Comparator.comparing(Genre::getId)).collect(Collectors.toCollection(LinkedHashSet::new)));
            return result;
        } else {
            return film;
        }
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        if (id < 0 || userId < 0) {
            throw new ValidationException("id must be positive");
        }
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        if (id < 0 || userId < 0) {
            throw new ValidationException("id must be positive");
        }
        filmService.removeLike(id, userId);
    }

    @GetMapping("/films/popular")
    public Collection<Film> findTopFilms(@RequestParam(value = "count", defaultValue = "10", required =
            false) int count) {
        return filmService.findTopFilms(count);
    }

    private void validateFilm(Film film) {
        if (film.getReleaseDate().before(ERAFILM)) {
            throw new FilmDateValidationException("Era of films starts 28Dec1895 ");
        }
        if (film.getDuration() < 0) {
            throw new FilmDateValidationException("Duration of film can't be negative");
        }
    }

}
