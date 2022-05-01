package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController controller = new FilmController();
    List<Film> list = new ArrayList<>();
    Film right = Film.builder()
            .id(0)
            .name("fjdyd")
            .description("kycdrt")
            .duration(Duration.ofMinutes(15L))
            .releaseDate(LocalDate.of(1896, 12, 28))
            .build();
    Film wrong = Film.builder()
            .id(1)
            .name("ydyd")
            .description("hgcktyd")
            .duration(Duration.ofMinutes(15L))
            .releaseDate(LocalDate.of(1895, 12, 27))
            .build();

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
        final Exception exception = assertThrows(ValidationException.class,
                () -> controller.create(wrong));
        assertEquals("Era of films starts 28Dec1895 ", exception.getMessage());
    }

    @Test
    void updateWrong() {
        wrong.setDuration(Duration.ofMinutes(-15L));
        wrong.setReleaseDate(LocalDate.of(1999, 10, 1));
        final Exception exception = assertThrows(ValidationException.class,
                () -> controller.update(wrong));
        assertEquals("Duration of film can't be negative", exception.getMessage());
    }
}