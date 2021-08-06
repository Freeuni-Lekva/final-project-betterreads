package Service;

import Model.User;

import java.util.List;

public interface AdminServiceInterface {

    boolean isAdmin(User user);

    void addAuthor(String author_name);

    void addGenre(String genre_name);
}
