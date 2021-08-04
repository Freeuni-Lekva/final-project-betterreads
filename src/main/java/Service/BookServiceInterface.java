package Service;

import Model.Book;

import java.sql.SQLException;

public interface BookServiceInterface {
    /**
     *
     * @param id
     * @return
     */
    Book getBookById(int id) throws SQLException;
}
