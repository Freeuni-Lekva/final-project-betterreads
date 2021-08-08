package Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CDB {
    private Connection connection;

    public CDB() throws SQLException {
        this.connection = Connector.getConnection("testLibrary");
        createDatabase();
        createAuthorsTable();
        createBooksTable();
        createGenresTable();
        createBookGenres();
        createUsersTable();
        createReservationsTable();
        createBookShelfTable();
        createReviewsTable();
        createLikesTable();
    }

    public void createDatabase() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop database if exists testLibrary;");
        statement.execute("create database  testLibrary;");
        statement.execute("use testLibrary;");
    }

    public void createAuthorsTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table if exists authors");
        statement.execute("create table if not exists authors( " +
                "    author_id int auto_increment primary key, " +
                "    author_name varchar(60) not null " +
                ");");
    }

    public void createBooksTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table if exists books");
        statement.execute("create table if not exists books(book_id int" +
                " auto_increment primary key,\n" +
                "    book_name varchar(60) not null,\n" +
                "    book_description varchar(6000),\n" +
                "    release_year int not null,\n" +
                "    author_id int not null,\n" +
                "    book_rating double,\n" +
                "    available_count int not null,\n" +
                "    book_photo varchar(600),\n" +
                "    constraint books_author_fk\n" +
                "        foreign key (author_id) references authors (author_id) );");
    }

    public void createGenresTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table if exists genres");
        statement.execute("create table if not exists genres(genre_id int" +
                " auto_increment primary key,\n" +
                "    genre_name varchar(60) not null);");
    }

    public void createBookGenres() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table if exists book_genres");
        statement.execute("create table if not exists book_genres(book_id int not null,\n" +
                "    genre_id int not null,\n" +
                "    constraint book_genres_book_fk \n" +
                "\t\tforeign key (book_id) references books(book_id),\n" +
                "    constraint book_genres_genre_fk\n" +
                "\t\tforeign key (genre_id) references genres(genre_id));");
    }

    public void createUsersTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table if exists users");
        statement.execute("create table if not exists users(user_id int auto_increment primary key,\n" +
                "    first_name varchar(60) not null,\n" +
                "    last_name varchar(60) not null,\n" +
                "    email varchar(60) not null,\n" +
                "    username varchar(60) not null,\n" +
                "    password_hash varchar(60) not null,\n" +
                "    constraint user_email_unique\n" +
                "                  unique (email),\n" +
                "    constraint username_unique\n" +
                "        unique (username));");
    }

    public void createReservationsTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table if exists reservations");
        statement.execute("create table if not exists reservations(reservation_id int auto_increment primary key,\n" +
                "\tuser_id int not null,\n" +
                "    book_id int not null,\n" +
                "    deadline date not null,\n" +
                "\tconstraint reservation_user_fk\n" +
                "\t\tforeign key (user_id) references users(user_id),\n" +
                "\tconstraint reservation_book_fk\n" +
                "\t\tforeign key (book_id) references books(book_id)  );");
    }

    public void createBookShelfTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table if exists book_shelf");
        statement.execute("create table if not exists book_shelf(user_id int not null,\n" +
                "    book_id int not null,\n" +
                "    already_read int not null,\n" +
                "    constraint book_shelf_user_fk\n" +
                "\t\tforeign key (user_id) references users(user_id),\n" +
                "\tconstraint book_shelf_book_fk\n" +
                "\t\tforeign key (book_id) references books(book_id)  );");
    }

    public void createReviewsTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table if exists reviews;");
        statement.execute("create table reviews(\n" +
                "\treview_id int auto_increment primary key,\n" +
                "\tuser_id int not null,\n" +
                "    book_id int not null,\n" +
                "    user_comment varchar(600),\n" +
                "    date_posted date not null,\n" +
                "    num_likes int not null,\n" +
                "    constraint reviews_user_fk\n" +
                "\t\tforeign key (user_id) references users(user_id),\n" +
                "\tconstraint reviews_book_fk\n" +
                "\t\tforeign key (book_id) references books(book_id)  \n" +
                ");");
    }

    public void createLikesTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("drop table if exists likes;");
        statement.execute("create table likes(\n" +
                "    like_id int auto_increment primary key,\n" +
                "    user_id int not null,\n" +
                "    review_id int not null,\n" +
                "    constraint likes_user_fk\n" +
                "        foreign key (user_id) references users(user_id),\n" +
                "    constraint likes_review_fk\n" +
                "        foreign key (review_id) references reviews(review_id)\n" +
                ");");
    }

    public Connection getConnection() {
        return connection;
    }
}
