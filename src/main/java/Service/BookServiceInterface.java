package Service;

import Model.Author;
import Model.Book;

import java.sql.SQLException;

import java.util.List;

public interface BookServiceInterface {

    /**
     *
     * @return list of all books
     */
    List<Book> getAllBooks();

    /**
     *
     * @param from index of first book
     * @param to index of last book
     * @return books sorted by rating - descending
     */
    List<Book> getBestBooks(int from, int to);

    /**
     *
     * @param from index of first book
     * @param to index of last book
     * @return books sorted by rating - ascending
     */
    List<Book> getLowRatingBooks(int from, int to);


    /**
     *
     * @param id
     * @return
     */
    Book getBookById(int id);

    /**
     *
     * @param book_id
     * @return
     */
    Author getAuthorById(int book_id);

    /**
     *
     * @return list of available books
     */
    List<Book> availableBooks();

    /**
     *
     * @param genres array of genres
     * @param list list of books to filter
     * @return
     */
    List<Book> getBooksByGanres(String[] genres, List<Book> list);


    int getBookCount(int book_id);

    void setBookCOunt(int book_id, int count);
}
