package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MpaDaoImpl implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_ALL_MPA = "SELECT * FROM mpa";
    private static final String GET_MPA_BY_ID = "SELECT * FROM mpa WHERE mpa_id = ?";

    @Autowired
    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MpaRating> getAllMpa() {
        return jdbcTemplate.query(GET_ALL_MPA, (rs, rowNum) -> mapRowToMpa(rs));
    }

    @Override
    public MpaRating getMpaById(Integer id) {
        final List<MpaRating> mpaRatings = jdbcTemplate.query(GET_MPA_BY_ID, (rs, rowNum) -> mapRowToMpa(rs), id);
        if (mpaRatings.size() > 0) {
            return mpaRatings.get(0);
        } else {
            return null;
        }
    }

    private MpaRating mapRowToMpa(ResultSet rs) throws SQLException {
        return MpaRating.builder().id(rs.getInt("mpa_id")).name(rs.getString("title")).build();
    }
}
