package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.validation.constraints.*;
import java.sql.Date;
import java.util.Set;

@Data
@Builder
public class Film {
    private Long id;
    @NotEmpty(message = "Name can't be empty")
    private String name;
    @NotBlank
    @Size(max = 200, message = "Description should be shorter")
    private String description;
    private Set<Genre> genres;
    @NotNull
    private MpaRating mpa;
    @PastOrPresent(message = "Realise should be in past or in present")
    private Date releaseDate;

    private Long duration;
}
