package Service;

import Model.Author;
import Model.User;

import java.sql.SQLException;
import java.util.List;

public interface AdminServiceInterface {

    public boolean isAdmin(User user);

    void addAuthor(String author_name) throws SQLException;

    void addGenre(String genre_name);

    boolean addBook(String book_name, Author author, int release_year, int count, String photo, String description, String[] genres);
  
    Author getAuthorByName(String author_name) throws SQLException;
}
