package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Film {
    @PositiveOrZero(message = "ID can't be negative")
    private long id;
    @NotEmpty(message = "Name can't be empty")
    private String name;
    @NotBlank
    @Size(max = 200, message = "Description should be shorter")
    private String description;
    @PastOrPresent(message = "Realise should be in past or in present")
    private LocalDate releaseDate;
    @JsonSerialize(using = CustomTimeSerializer.class)
    private Duration duration;
    @NonNull
    private Set<Long> likes=new HashSet<>();



    public void addLike(Long id) {
        likes.add(id);
    }

    public void removeLike(Long id) {
        likes.remove(id);
    }
}
