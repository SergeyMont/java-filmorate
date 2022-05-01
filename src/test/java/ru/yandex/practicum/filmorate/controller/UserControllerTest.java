package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserController controller = new UserController();
    User right = User.builder()
            .id(0).name("eree")
            .login("trdvbh")
            .birth(LocalDate.of(1900, 10, 10))
            .email("tret@mail.ru")
            .build();

    List<User> list = new ArrayList<>();

    @Test
    void findAllUsers() {
        assertEquals(list, controller.findAllUsers());
    }

    @Test
    void createRight() {
        list.add(right);
        controller.create(right);
        assertEquals(list, controller.findAllUsers());
    }

    @Test
    void updateRight() {
        right.setName("ewrtty");
        right.setLogin("treee");
        list.add(right);
        controller.create(right);
        assertEquals(list, controller.findAllUsers());
    }

}