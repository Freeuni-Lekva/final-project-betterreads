package Service;

import Constants.SharedConstants;
import Dao.BookDao;
import Model.Book;

import java.sql.SQLException;

public class BookService implements BookServiceInterface{

    private BookDao bd;

    public BookService() throws SQLException {
        bd = new BookDao(SharedConstants.DATA_BASE_NAME);
    }

    @Override
    public Book getBookById(int id) throws SQLException {
        return bd.getBookById(id);
    }
}
