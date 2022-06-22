package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exception.FilmDateValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmDaoStorage;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmControllerTest {
    @Autowired
    private FilmController controller;
    List<Film> list = new ArrayList<>();
    Film right = Film.builder()
            .name("jrtd")
            .description("khgcky")
            .releaseDate(Date.valueOf(LocalDate.of(1896, 12, 28)))
            .duration(15L)
            .mpa(MpaRating.builder().id(1).name("G").build())
            .build();
    Film wrong = Film.builder()
            .name("jrtd")
            .description("khgcky")
            .releaseDate(Date.valueOf(LocalDate.of(1895, 12, 27)))
            .duration(15L)
            .mpa(MpaRating.builder().id(1).name("G").build())
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
        final Exception exception = assertThrows(FilmDateValidationException.class,
                () -> controller.create(wrong));
        assertEquals("Era of films starts 28Dec1895 ", exception.getMessage());
    }

    @Test
    void updateWrong() {
        wrong.setId(2L);
        wrong.setDuration(-15L);
        wrong.setReleaseDate(Date.valueOf(LocalDate.of(1999, 10, 1)));
        final Exception exception = assertThrows(FilmDateValidationException.class,
                () -> controller.update(wrong));
        assertEquals("Duration of film can't be negative", exception.getMessage());
    }
}