package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.FilmDaoStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FilmDaoImpl implements FilmDaoStorage, LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getAll() {
        final Map<Long, Set<Genre>> filmsGenres = getAllFilmsGenres();
        final String query = "SELECT * FROM film LEFT JOIN mpa ON film.mpa_id = mpa.mpa_id";
        return jdbcTemplate.query(query, (rs, numRow) -> {
            final Long filmId = rs.getLong("film_id");
            return mapRowToFilm(rs, filmsGenres.get(filmId));
        });
    }

    @Override
    public Film create(Film film) {
        final String query = "INSERT INTO film ( name, description, release_date, duration, mpa_id)"
                + " VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int good = jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(query, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, film.getReleaseDate());
            stmt.setLong(4, film.getDuration());
            stmt.setLong(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().longValue());

        updateGenres(film);
        if (good > 0) return film;
        return null;
    }

    @Override
    public Film update(Film film) {
        final String sql = "UPDATE film SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ?"
                + " WHERE film_id = ?";

        int good = jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());

        final String deleteGenres = "DELETE FROM film_genres WHERE film_id = ?";
        jdbcTemplate.update(deleteGenres, film.getId());

        updateGenres(film);
        if (good > 0) return film;
        return null;
    }

    private void updateGenres(Film film) {
        final Set<Genre> filmGenres = film.getGenres();

        if (filmGenres != null) {
            final String genreSaveSql = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
            filmGenres.forEach(x -> jdbcTemplate.update(genreSaveSql, film.getId(), x.getId()));
        }
    }

    @Override
    public Film findById(Long id) {
        final String query = "SELECT * FROM film LEFT JOIN mpa ON film.mpa_id = mpa.mpa_id WHERE film_id = ?";
        List<Film> films = jdbcTemplate.query(query, (rs, numRow) -> mapRowToFilm(rs, getFilmGenresById(id)), id);
        if (films.size() > 0) {
            return films.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Collection<Film> getPopularFilms(Integer limit) {
        final String query = "SELECT * FROM film f LEFT JOIN (SELECT film_id, COUNT(*) likes_count FROM likes"
                + " GROUP BY film_id) l ON f.film_id = l.film_id LEFT JOIN mpa ON f.mpa_id = mpa.mpa_id"
                + " ORDER BY l.likes_count DESC LIMIT ?";

        final Map<Long, Set<Genre>> filmsGenres = getAllFilmsGenres();

        return jdbcTemplate.query(query, (rs, numRow) -> {
            final Long filmId = rs.getLong("film_id");
            return mapRowToFilm(rs, filmsGenres.get(filmId));
        }, limit);
    }

    @Override
    public void save(Like like) {
        final String query = "INSERT INTO likes (user_id, film_id) VALUES (?, ?)";
        jdbcTemplate.update(query, like.getUserId(), like.getFilmId());
    }

    @Override
    public void delete(Like like) {
        final String query = "DELETE FROM likes WHERE user_id = ? AND film_id = ?";
        jdbcTemplate.update(query, like.getUserId(), like.getFilmId());
    }

    private Set<Genre> getFilmGenresById(Long id) {
        final String sql = "SELECT * FROM film_genres JOIN genres ON genres.genre_id = film_genres.genre_id"
                + " WHERE film_id = ?";

        Set<Genre> genreSet = new HashSet<>();
        List<Genre> list;
        list = jdbcTemplate.query(sql, (rs, getNum) -> Genre.builder().id(rs.getInt("genre_id"))
                .name(rs.getString("name")).build(), id);
        genreSet.addAll(list);
        return genreSet.stream().sorted(Comparator.comparing(Genre::getId)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Film mapRowToFilm(ResultSet rs, Set<Genre> genres) throws SQLException {
        return Film.builder()
                .id(rs.getLong("film_id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date"))
                .duration(rs.getLong("duration"))
                .genres(genres != null && genres.isEmpty() ? null : genres)
                .mpa(MpaRating.builder().id(rs.getInt("mpa_id")).name(rs.getString("title")).build())
                .build();
    }

    private Map<Long, Set<Genre>> getAllFilmsGenres() {
        final String sql = "SELECT * FROM film_genres JOIN genres ON genres.genre_id = film_genres.genre_id";

        final Map<Long, Set<Genre>> filmsGenres = new HashMap<>();

        jdbcTemplate.query(sql, (RowCallbackHandler) rs -> {
            final Long filmId = rs.getLong("film_id");
            filmsGenres.getOrDefault(filmId, new HashSet<>()).add(Genre.builder().id(rs.getInt("genre_id"))
                    .name(rs.getString("name")).build());
        });

        return filmsGenres;
    }
}
