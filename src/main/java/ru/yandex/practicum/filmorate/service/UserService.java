package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public Set<User> allFriends(Long id) {
        Set<User> set = new HashSet<>();
        List<Long> list = new ArrayList<>();
        list.addAll(userStorage.findById(id).getFriends());
        for (int i = 0; i < list.size(); i++) {
            set.add(userStorage.findById(list.get(i)));
        }
        return set;
    }

    public Set<User> findOurFriends(Long id, Long idFriend) {
        Set<User> set = new HashSet<>();
        Set<Long> setNumber = new HashSet<>();
        List<Long> list = new ArrayList<>();
        setNumber.addAll(userStorage.findById(id).getFriends());
        setNumber.addAll(userStorage.findById(idFriend).getFriends());
        list.addAll(setNumber);
        for (int i = 0; i < list.size(); i++) {
            set.add(userStorage.findById(list.get(i)));
        }
        return set;
    }
}
