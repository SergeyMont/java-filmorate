CREATE TABLE IF NOT EXISTS mpa
(
    mpa_id INTEGER auto_increment NOT NULL PRIMARY KEY,
    title  VARCHAR(255)          NOT NULL
);

CREATE TABLE IF NOT EXISTS film
(
    film_id      LONG auto_increment NOT NULL PRIMARY KEY,
    name         VARCHAR      NULL    ,
    description  VARCHAR(200) NULL    ,
    release_date DATE         NULL    ,
    duration     LONG    NULL    ,
    mpa_id    LONG         NOT NULL REFERENCES mpa(mpa_id)
);

CREATE TABLE IF NOT EXISTS genres
(
    genre_id INT     NOT NULL,
    name     VARCHAR NULL    ,
    PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS film_genres
(
    film_id  LONG NOT NULL REFERENCES film(film_id),
    genre_id INT NOT NULL REFERENCES genres(genre_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id  LONG     NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email    VARCHAR NULL    ,
    login    VARCHAR NULL    ,
    name     VARCHAR NULL    ,
    birthday DATE    NULL
);

CREATE TABLE IF NOT EXISTS friend_accepted
(
    first_user_id LONG NOT NULL REFERENCES users (user_id),
    second_user_id LONG NOT NULL REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS friend_request
(
    first_user_id LONG NOT NULL REFERENCES users (user_id),
    second_user_id LONG NOT NULL REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS likes
(
    film_id LONG NOT NULL REFERENCES film(film_id),
    user_id LONG NOT NULL REFERENCES users(user_id)
);



