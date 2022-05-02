package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addToFriend(Long id, Long idFriend) {
        userStorage.findById(id).addFriend(idFriend);
        userStorage.findById(idFriend).addFriend(id);
    }

    public void removeFromFriends(Long id, Long idFriend) {
        userStorage.findById(id).removeFriend(idFriend);
        userStorage.findById(idFriend).removeFriend(id);
    }

    public Set<Long> allFriends(Long id) {
        return userStorage.findById(id).getFriends();
    }

    public Set<Long> findOurFriends(Long id, Long idFriend) {
        Set<Long> set = new HashSet<>();
        set.addAll(userStorage.findById(id).getFriends());
        set.addAll(userStorage.findById(idFriend).getFriends());
        return set;
    }
}
