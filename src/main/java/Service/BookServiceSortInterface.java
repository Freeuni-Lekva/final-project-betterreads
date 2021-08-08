package Service;

import Model.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookServiceSortInterface {

    /**
     *
     * @param bookList
     * @return list of books sorted by release year - descending
     */
    List<Book> oldToNew(List<Book> bookList) throws SQLException;

    /**
     *
     * @param bookList
     * @return list of books sorted by release year - ascending
     */
    List<Book> newToOld(List<Book> bookList) throws SQLException;

    /**
     *
     * @param bookList
     * @return list of book sorted by rating - ascending
     */
    List<Book> sortLowToHigh(List<Book> bookList);

    /**
     *
     * @param bookList
     * @return list of book sorted by rating - descending
     */
    List<Book> sortHighToLow(List<Book> bookList);

    /**
     *
     * @param list
     * @return remove unavailable books
     */
    List<Book> removeUnavailableBooks(List<Book> list);
}
