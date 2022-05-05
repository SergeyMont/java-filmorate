package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage){
        this.filmStorage=filmStorage;
    }

    public void addLike(Long id, Long userId){
        filmStorage.findById(id).addLike(userId);
    }

    public void removeLike(Long id, Long userId){
        filmStorage.findById(id).removeLike(userId);
    }

    public List<Film> findTopFilms(int count){
        return filmStorage.getAll().stream()
                .sorted((f1,f2)->f1.getLikes().size()-f2.getLikes().size())
                .limit(count)
                .collect(Collectors.toUnmodifiableList());
    }
}
