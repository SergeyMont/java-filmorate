package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.FilmDaoStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmDaoStorage filmStorage;
    private final LikeStorage likeStorage;

    @Autowired
    public FilmService(@Qualifier("filmDaoImpl") FilmDaoStorage filmStorage, LikeStorage likeStorage) {

        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
    }

    public List<Film> findAll() {
        return filmStorage.getAll();
    }

    public Film findById(Long id) {
        return filmStorage.findById(id);
    }

    public Film createFilm(Film film) {
        return filmStorage.create(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
    }

    public void addLike(Long id, Long userId) {
        likeStorage.save(Like.builder().filmId(id).userId(userId).build());
    }

    public void removeLike(Long id, Long userId) {
        likeStorage.delete(Like.builder().filmId(id).userId(userId).build());
    }

    public Collection<Film> findTopFilms(int count) {
        return likeStorage.getPopularFilms(count);
    }
}
