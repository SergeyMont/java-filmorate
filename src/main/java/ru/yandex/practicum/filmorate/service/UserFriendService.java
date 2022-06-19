package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendDaoStorage;
import ru.yandex.practicum.filmorate.storage.UserDaoStorage;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserFriendService extends UserService implements UserServiceInterface {
    private final FriendDaoStorage friendDaoStorage;

    @Autowired
    public UserFriendService(@Qualifier("userDaoImpl") UserDaoStorage userStorage, FriendDaoStorage friendDaoStorage) {
        super(userStorage);
        this.friendDaoStorage = friendDaoStorage;
    }

    @Override
    public void addToFriend(Long id, Long idFriend) {
        friendDaoStorage.addRequest(id, idFriend);
    }

    @Override
    public void removeFromFriends(Long id, Long idFriend) {
        friendDaoStorage.removeRequest(id, idFriend);
    }

    @Override
    public Set<User> allFriends(Long id) {
        return friendDaoStorage.getRequests(id);
    }

    @Override
    public Set<User> findOurFriends(Long id, Long idFriend) {

        Set<User> intersection = new HashSet<>(friendDaoStorage.getRequests(id));
        intersection.retainAll(friendDaoStorage.getRequests(idFriend));

        return intersection;
    }
}
