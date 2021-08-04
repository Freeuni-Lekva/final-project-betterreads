package Dao;

import Model.Book;
import Model.Genre;

import java.sql.SQLException;
import java.util.List;

public interface BookGenreDaoInterface {
    /**
     *
     * @param bookID
     * @param genreIds
     * @return
     * @throws SQLException
     */
    public boolean addBookGenres(int bookID, List<Integer> genreIds) throws SQLException;

    /**
     *
     * @param bookID
     * @return
     * @throws SQLException
     */
    public List<Genre> getBookGenres(int bookID) throws SQLException;
}
