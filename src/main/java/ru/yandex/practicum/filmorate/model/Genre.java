package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class Genre {
    Integer id;
    @NotBlank
    String name;

    @JsonCreator
    public static Genre forObject(@JsonProperty("id") Integer id, @JsonProperty String name) {
        return new Genre(id, name);
    }
}
