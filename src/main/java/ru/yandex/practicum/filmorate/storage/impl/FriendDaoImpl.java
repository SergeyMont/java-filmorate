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

    @Autowired
    public FriendDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Set<User> getRequests(Long id) {
        String query = "select user_id, email, login, name, birthday from users as u join friend_request as r on u.user_id=r.second_user_id where first_user_id=?";
        Set<User> set = new HashSet<>();
        List<User> list = jdbcTemplate.query(query, this::mapRowToUser, id);
        set.addAll(list);
        return set;
    }

    @Override
    public boolean addRequest(Long id, Long friendId) {
        String query = "insert into friend_request(first_user_id, second_user_id) values(?,?)";
        return jdbcTemplate.update(query, id, friendId) > 0;
    }

    @Override
    public boolean removeRequest(Long id, Long friendId) {
        String query = "delete from friend_request where first_user_id=? and second_user_id=?";
        return jdbcTemplate.update(query, id, friendId) > 0;
    }

    @Override
    public Set<User> getFriends(Long id) {
        String query = "select user_id, email, login, name, birthday from users as u join friend_accepted as r on u.user_id=r.second_user_id where first_user_id=?";
        return (Set<User>) jdbcTemplate.query(query, this::mapRowToUser, id);
    }

    @Override
    public boolean addFriendship(Long id, Long friendId) {
        String query = "insert into friend_accepted(first_user_id, second_user_id) values(?,?)";
        return jdbcTemplate.update(query, id, friendId) > 0;
    }

    @Override
    public boolean removeFriendship(Long id, Long friendId) {
        String query = "delete from friend_accepted where first_user_id=? and second_user_id=?";
        return jdbcTemplate.update(query) > 0;
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
