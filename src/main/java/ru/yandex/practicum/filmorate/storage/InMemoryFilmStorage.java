package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> map = new HashMap<>();

    @Override
    public List<Film> getAll() {
        List<Film>list=new ArrayList<>();
        list.addAll(map.values());
        return list;
    }

    @Override
    public void create(Film film) {
        map.put(film.getId(), film);
    }

    @Override
    public void update(Film film) {
        map.put(film.getId(), film);
    }

    @Override
    public Film findById(Long id) {
        return map.get(id);
    }
}
