package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class User {

    private long id;
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Enter correct email format")
    private String email;
    @NotEmpty(message = "Login can't be empty")
    @NotBlank(message = "Delete blank")
    private String login;
    private String name;
    @Past(message = "You can't birth in future")
    private Date birthday;
    private Set<Long> friends;
    private Map<Long, Boolean> friendship;

    public void addFriend(Long id) {
        friends = new HashSet<>();
        friendship = new HashMap<>();
        friends.add(id);
        friendship.put(id, false);
    }

    public void removeFriend(Long id) {

        friends.remove(id);
        friendship.remove(id);
    }
}
