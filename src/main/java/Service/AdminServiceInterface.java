package Service;

import Model.Author;
import Model.User;

import java.util.List;

public interface AdminServiceInterface {

    public boolean isAdmin(User user);

    public Author getAuthorByName(String author_name);

    public boolean addBook(String book_name,Author author,int release_year,int count, String photo, String description, String[] genres);
}
