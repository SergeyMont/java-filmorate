package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDaoStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDaoStorage {

    private final JdbcTemplate jdbcTemplate;
    private static final String GET_ALL_USERS = "SELECT user_id, email, login, name, birthday FROM users";
    private static final String INSERT_USER = "INSERT INTO users(email, login, name, birthday) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_USER = "update users set email=?, login=?, name=?, birthday=? where user_id=?";
    private static final String SELECT_USER_BY_ID = "select user_id, email, login, name, birthday from users where user_id=?";

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(GET_ALL_USERS, this::mapRowToUser);
    }

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(INSERT_USER, new String[]{"user_id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, user.getBirthday());
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public User update(User user) {
        int up = jdbcTemplate.update(UPDATE_USER,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        if (up > 0) return user;
        else return null;
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_USER_BY_ID, this::mapRowToUser, id);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("user_id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday"))
                .build();
    }
}
