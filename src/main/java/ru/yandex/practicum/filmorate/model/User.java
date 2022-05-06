package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {
    @PositiveOrZero(message = "ID can't be negative")
    private long id;
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Enter correct email format")
    private String email;
    @NotEmpty(message = "Login can't be empty")
    @NotBlank(message = "Delete blank")
    private String login;
    private String name;
    @Past(message = "You can't birth in future")
    private LocalDate birthday;
    private Set<Long> friends;

    public void addFriend(Long id){
        friends=new HashSet<>();
        friends.add(id);
    }

    public void removeFriend(Long id){
        friends.remove(id);
    }
}
