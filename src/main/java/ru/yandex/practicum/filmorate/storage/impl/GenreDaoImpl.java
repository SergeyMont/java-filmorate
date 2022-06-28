package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class GenreDaoImpl implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_ALL_GENRE = "SELECT * FROM genres";
    private static final String GET_GENRE_BY_ID = "SELECT * FROM genres WHERE genre_id = ?";

    @Autowired
    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAllGenre() {
        return jdbcTemplate.query(GET_ALL_GENRE, (rs, rowNum) -> mapRowToMpa(rs));
    }

    @Override
    public Genre getGenreById(Integer id) {
        final List<Genre> genres = jdbcTemplate.query(GET_GENRE_BY_ID, (rs, rowNum) -> mapRowToMpa(rs), id);
        if (genres.size() > 0) {
            return genres.get(0);
        } else {
            return null;
        }
    }

    private Genre mapRowToMpa(ResultSet rs) throws SQLException {
        return Genre.builder().id(rs.getInt("genre_id")).name(rs.getString("name")).build();
    }
}
