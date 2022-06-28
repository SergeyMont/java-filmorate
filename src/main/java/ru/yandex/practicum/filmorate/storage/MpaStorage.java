package ru.yandex.practicum.filmorate.storage;

import org.springframework.data.relational.core.sql.In;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

public interface MpaStorage {

    List<MpaRating> getAllMpa();

    MpaRating getMpaById(Integer id);
}
