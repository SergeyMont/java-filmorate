package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {
    @Autowired
    private UserController controller;
    User right = User.builder()
            .id(0).name("eree")
            .login("trdvbh")
            .birthday(Date.valueOf(LocalDate.of(1900, 10, 10)))
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