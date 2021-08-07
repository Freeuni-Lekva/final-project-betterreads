package Service;

import Model.Book;
import Model.User;

import java.util.List;

public interface SuggestionServiceInterface {
    List<Book> suggestByUser(User user);
}
