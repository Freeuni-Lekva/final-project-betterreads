drop database if exists testLibrary;
create database testLibrary;
use testLibrary;

drop table if exists authors;
create table authors(
    author_id int auto_increment primary key,
    author_name varchar(60) not null
);

drop table if exists books;
create table books(
  book_id int auto_increment primary key,
    book_name varchar(60) not null,
    book_description varchar(6000),
    release_year int not null,
    author_id int not null,
    book_rating double,
    available_count int not null,
    book_photo varchar(600),
    constraint books_author_fk
        foreign key (author_id) references authors (author_id)
);

drop table if exists genres;
create table genres(
  genre_id int auto_increment primary key,
    genre_name varchar(60) not null
);

drop table if exists book_genres;
create table book_genres(
  book_id int not null,
    genre_id int not null,
    constraint book_genres_book_fk 
    foreign key (book_id) references books(book_id),
    constraint book_genres_genre_fk
    foreign key (genre_id) references genres(genre_id)
);

drop table if exists users;
create table users(
  user_id int auto_increment primary key,
    first_name varchar(60) not null,
    last_name varchar(60) not null,
    email varchar(60) not null,
    username varchar(60) not null,
    password_hash varchar(60) not null,
    constraint user_email_unique
                  unique (email),
  constraint username_unique
      unique (username)
);

drop table if exists reservations;
create table reservations(
  reservation_id int auto_increment primary key,
  user_id int not null,
    book_id int not null,
    deadline date not null,
  constraint reservation_user_fk
    foreign key (user_id) references users(user_id),
  constraint reservation_book_fk
    foreign key (book_id) references books(book_id)  
);

drop table if exists book_shelf;
create table book_shelf(
  user_id int not null,
    book_id int not null,
    already_read int not null,
    constraint book_shelf_user_fk
    foreign key (user_id) references users(user_id),
  constraint book_shelf_book_fk
    foreign key (book_id) references books(book_id)  
);

drop table if exists reviews;
create table reviews(
  review_id int auto_increment primary key,
  user_id int not null,
    book_id int not null,
    book_rating double not null,
    user_comment varchar(600),
    date_posted date not null,
    num_likes int not null,
    constraint reviews_user_fk
    foreign key (user_id) references users(user_id),
  constraint reviews_book_fk
    foreign key (book_id) references books(book_id)  
);
