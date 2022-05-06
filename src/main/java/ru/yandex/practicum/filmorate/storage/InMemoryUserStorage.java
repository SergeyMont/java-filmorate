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
    private  static Long storageId=1L;
    @Override
    public List<User> getAll() {
        List<User>list= new ArrayList<>();
        list.addAll(map.values());
        return list;
    }

    @Override
    public User create(User user) {
        user.setId(getNextId());
        System.out.println(user.toString());
        return map.put(user.getId(), user);
    }

    @Override
    public User update(User user) {
        return map.put(user.getId(), user);
    }

    @Override
    public User findById(Long id) {
        return map.getOrDefault(id,null);
    }

    private static Long getNextId (){return storageId++;}
}
