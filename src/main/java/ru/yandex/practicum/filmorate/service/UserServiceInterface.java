package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface UserServiceInterface {
    void addToFriend(Long id, Long idFriend);

    void removeFromFriends(Long id, Long idFriend);

    Set<User> allFriends(Long id);

    Set<User> findOurFriends(Long id, Long idFriend);
}
