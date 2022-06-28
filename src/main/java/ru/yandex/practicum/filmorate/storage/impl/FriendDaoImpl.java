package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendDaoStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class FriendDaoImpl implements FriendDaoStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_REQUESTS = "SELECT user_id, email, login, name, birthday FROM users AS u"
            + " JOIN friend_request AS r ON u.user_id=r.second_user_id WHERE first_user_id=?";
    private static final String INSERT_REQUEST = "INSERT INTO friend_request(first_user_id, second_user_id) VALUES(?,?)";
    private static final String DELETE_REQUEST = "DELETE FROM friend_request WHERE first_user_id=? AND second_user_id=?";
    private static final String GET_FRIENDSHIP = "SELECT user_id, email, login, name, birthday FROM users AS u"
            + " JOIN friend_accepted AS r ON u.user_id=r.second_user_id WHERE first_user_id=?";
    private static final String INSERT_FRIENDSHIP = "INSERT INTO friend_accepted(first_user_id, second_user_id) VALUES (?,?)";
    private static final String DELETE_FRIENDSHIP = "DELETE FROM friend_accepted WHERE first_user_id=? AND second_user_id=?";

    @Autowired
    public FriendDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Set<User> getRequests(Long id) {
        Set<User> set = new HashSet<>();
        List<User> list = jdbcTemplate.query(GET_REQUESTS, this::mapRowToUser, id);
        set.addAll(list);
        return set;
    }

    @Override
    public boolean addRequest(Long id, Long friendId) {
        return jdbcTemplate.update(INSERT_REQUEST, id, friendId) > 0;
    }

    @Override
    public boolean removeRequest(Long id, Long friendId) {

        return jdbcTemplate.update(DELETE_REQUEST, id, friendId) > 0;
    }

    @Override
    public Set<User> getFriends(Long id) {
        return (Set<User>) jdbcTemplate.query(GET_FRIENDSHIP, this::mapRowToUser, id);
    }

    @Override
    public boolean addFriendship(Long id, Long friendId) {
        return jdbcTemplate.update(INSERT_FRIENDSHIP, id, friendId) > 0;
    }

    @Override
    public boolean removeFriendship(Long id, Long friendId) {
        return jdbcTemplate.update(DELETE_FRIENDSHIP) > 0;
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
