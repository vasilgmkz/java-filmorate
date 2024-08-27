insert into USERS (email, login, user_name, birthday)
values ('email@email.com', 'user', 'test', '2000-03-22');

INSERT INTO GENRES (genre_name)
VALUES ('Боевик');

INSERT INTO Rating (rating_name)
VALUES ('G');

insert into FILMS (name, rating_id, description, releaseDate, duration)
values ('name', 1, 'description', '2022-04-12', 200);

insert into FilmsGenres (film_id, genre_id)
values (1, 1);



