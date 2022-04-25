package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    @PositiveOrZero(message = "ID can't be negative")
    private long id;
    @NotEmpty(message = "Name can't be empty")
    private String name;
    @Size(max = 200, message = "Description should be shorter")
    private String description;
    @PastOrPresent(message = "Realise should be in past or in present")
    private LocalDate releaseDate;

    private Duration duration;
}
