package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Set;

public interface FriendDaoStorage {

    Set<User> getRequests(Long id);

    boolean addRequest(Long id, Long friendId);

    boolean removeRequest(Long id, Long friendId);

    Set<User> getFriends(Long id);

    boolean addFriendship(Long id, Long friendId);

    boolean removeFriendship(Long id, Long friendId);
}
