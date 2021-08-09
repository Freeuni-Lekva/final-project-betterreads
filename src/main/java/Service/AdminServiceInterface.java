package Service;

import Model.Author;
import Model.Book;
import Model.User;

import java.sql.SQLException;
import java.util.List;

public interface AdminServiceInterface {

    public boolean isAdmin(User user) throws SQLException;

    void addAuthor(String author_name) throws SQLException;

    void addGenre(String genre_name);

    boolean addBook(String book_name, Author author, int release_year, int count, String photo, String description, String[] genres) throws SQLException;
  
    Author getAuthorByName(String author_name) throws SQLException;

    public void changeBookCount(Book book, int count) throws SQLException;
}
