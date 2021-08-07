package Service;

import Constants.SharedConstants;
import Dao.BookDao;
import Dao.Connector;
import Model.Book;

import java.sql.SQLException;
import java.util.List;

public interface SearchServiceInterface {
    //    public
    List<Book> filterByText(String text);

    int levDistance(String s1, String s2);
}