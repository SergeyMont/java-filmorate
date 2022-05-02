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

    public void addLike(Long id){
        filmStorage.findById(id).addLike(id);
    }

    public void removeLike(Long id){
        filmStorage.findById(id).removeLike(id);
    }

    public List<Film> findTopFilms(){
        return filmStorage.getAll().stream()
                .sorted((f1,f2)->f1.getLikes().size()-f2.getLikes().size())
                .limit(10)
                .collect(Collectors.toUnmodifiableList());
    }
}
