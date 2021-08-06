package Dao;

import Model.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookGenreDao implements BookGenreDaoInterface{
    Connection connection;

    public BookGenreDao(Connection connection) {
        this.connection = connection;
    }


    public boolean addBookGenres(int bookID, List<Integer> genreIds) throws SQLException {
        for (Integer genreID : genreIds) {
            PreparedStatement statement;
            statement = connection.prepareStatement("insert into book_genres (book_id, genre_id)" +
                    "values(?,?);");
            statement.setInt(1, bookID);
            statement.setInt(2, genreID);
            if (statement.executeUpdate() == 0) return false;
        }
        return true;
    }

    private Genre getGenreByRS(ResultSet rs) throws SQLException {
        Genre res = new Genre();
        res.setGenre_id(rs.getInt(2));
        res.setGenre_name(rs.getString(4));
        return res;
    }

    @Override
    public List<Genre> getBookGenres(int bookID) throws SQLException {
        PreparedStatement statement;
        statement = connection.prepareStatement("select * from book_genres " +
                "join genres on book_genres.genre_id = genres.genre_id " +
                "where book_id = ?;");
        statement.setInt(1, bookID);
        ResultSet rs = statement.executeQuery();
        List<Genre> genres = new ArrayList<>();
        while(rs.next())
            genres.add(getGenreByRS(rs));
        return genres;
    }
}
