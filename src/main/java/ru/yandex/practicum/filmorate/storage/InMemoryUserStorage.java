package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage{
    private final Map<Long,User> map=new HashMap<>();
    @Override
    public List<User> getAll() {
        List<User>list= new ArrayList<>();
        list.addAll(map.values());
        return list;
    }

    @Override
    public void create(User user) {
        map.put(user.getId(), user);
    }

    @Override
    public void update(User user) {
        map.put(user.getId(), user);
    }

    @Override
    public User findById(Long id) {
        return map.getOrDefault(id,null);
    }
}
