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

    @Autowired
    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAllGenre() {
        String query = "SELECT * FROM genres";
        return jdbcTemplate.query(query, (rs, rowNum) -> mapRowToMpa(rs));
    }

    @Override
    public Genre getGenreById(Integer id) {
        String query = "SELECT * FROM genres WHERE genre_id = ?";
        final List<Genre> genres = jdbcTemplate.query(query, (rs, rowNum) -> mapRowToMpa(rs), id);
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
