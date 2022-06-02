# java-filmorate
Template repository for Filmorate project.
![This is ER-diagtam](https://github.com/SergeyMont/java-filmorate/blob/add-friends-likes/filmorate.png)

        
CREATE TABLE film
(
  film_id      INT          NOT NULL AUTO_INCREMENT,
  name         VARCHAR      NULL    ,
  description  VARCHAR(200) NULL    ,
  release_date DATE         NULL    ,
  duration     TIMESTAMP    NULL    ,
  rating_id    INT          NOT NULL,
  PRIMARY KEY (film_id)
);

CREATE TABLE film_genre
(
  film_id  INT NOT NULL,
  genre_id INT NOT NULL
);

CREATE TABLE friend_accepted
(
  user_id INT NOT NULL,
  user_id INT NOT NULL
);

CREATE TABLE friend_reqest
(
  user_id INT NOT NULL,
  user_id INT NOT NULL
);

CREATE TABLE genre
(
  genre_id INT     NOT NULL,
  name     VARCHAR NULL    ,
  PRIMARY KEY (genre_id)
);

CREATE TABLE like
(
  film_id INT NOT NULL,
  user_id INT NOT NULL
);

CREATE TABLE rating
(
  rating_id   INT     NOT NULL,
  name        VARCHAR NULL    ,
  description VARCHAR NULL    ,
  PRIMARY KEY (rating_id)
);

CREATE TABLE user
(
  user_id  INT     NOT NULL AUTO_INCREMENT,
  email    VARCHAR NULL    ,
  login    VARCHAR NULL    ,
  name     VARCHAR NULL    ,
  birthday DATE    NULL    ,
  PRIMARY KEY (user_id)
);

ALTER TABLE like
  ADD CONSTRAINT FK_film_TO_like
    FOREIGN KEY (film_id)
    REFERENCES film (film_id);

ALTER TABLE like
  ADD CONSTRAINT FK_user_TO_like
    FOREIGN KEY (user_id)
    REFERENCES user (user_id);

ALTER TABLE film
  ADD CONSTRAINT FK_rating_TO_film
    FOREIGN KEY (rating_id)
    REFERENCES rating (rating_id);

ALTER TABLE friend_reqest
  ADD CONSTRAINT FK_user_TO_friend_reqest
    FOREIGN KEY (user_id)
    REFERENCES user (user_id);

ALTER TABLE friend_reqest
  ADD CONSTRAINT FK_user_TO_friend_reqest1
    FOREIGN KEY (user_id)
    REFERENCES user (user_id);

ALTER TABLE friend_accepted
  ADD CONSTRAINT FK_user_TO_friend_accepted
    FOREIGN KEY (user_id)
    REFERENCES user (user_id);

ALTER TABLE friend_accepted
  ADD CONSTRAINT FK_user_TO_friend_accepted1
    FOREIGN KEY (user_id)
    REFERENCES user (user_id);

ALTER TABLE film_genre
  ADD CONSTRAINT FK_film_TO_film_genre
    FOREIGN KEY (film_id)
    REFERENCES film (film_id);

ALTER TABLE film_genre
  ADD CONSTRAINT FK_genre_TO_film_genre
    FOREIGN KEY (genre_id)
    REFERENCES genre (genre_id);
