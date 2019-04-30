CREATE DATABASE hangman;

USE hangman;

CREATE TABLE words(
    id_word int auto_increment,
    word varchar(20),
    constraint pk_words primary key (id_word)
);

CREATE TABLE winners(
    id_winner int auto_increment,
    name varchar(50),
    id_word int,
    date_win datetime,
    constraint pk_winner primary key (id_winner),
    constraint fk_winners_id_word foreign key (id_word) references words(id_word)
);

insert into words (word) values 
('hola'),
('dedo'),
('dado'),
('arco'),
('educacion'),
('ecuacion'),
('pato'),
('pata'),
('chau'),
('adios');


select word from words order by rand() limit 1;