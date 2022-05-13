package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.FilmDateValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmStorage storage = new InMemoryFilmStorage();
    FilmService filmService = new FilmService(storage);
    FilmController controller = new FilmController(filmService, storage);
    List<Film> list = new ArrayList<>();
    Film right = new Film(0, "fjdyd", "kycdrt", LocalDate.of(1896, 12, 28),
            Duration.ofMinutes(15L));

    Film wrong = new Film(1, "ydyd", "hgcktyd", LocalDate.of(1895, 12, 27),
            Duration.ofMinutes(15L));

    @Test
    void findAllUsers() {
        assertEquals(list, controller.findAllFilms());
    }

    @Test
    void createRight() {
        list.add(right);
        controller.create(right);
        assertEquals(list, controller.findAllFilms());
    }

    @Test
    void updateRight() {
        right.setName("ghcjfj");
        list.add(right);
        controller.create(right);
        assertEquals(list, controller.findAllFilms());
    }

    @Test
    void createWrong() {
        final Exception exception = assertThrows(FilmDateValidationException.class,
                () -> controller.create(wrong));
        assertEquals("Era of films starts 28Dec1895 ", exception.getMessage());
    }

    @Test
    void updateWrong() {
        wrong.setDuration(Duration.ofMinutes(-15L));
        wrong.setReleaseDate(LocalDate.of(1999, 10, 1));
        final Exception exception = assertThrows(FilmDateValidationException.class,
                () -> controller.update(wrong));
        assertEquals("Duration of film can't be negative", exception.getMessage());
    }
}