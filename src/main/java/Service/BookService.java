package Service;

import Constants.SharedConstants;
import Dao.AuthorDao;
import Dao.BookDao;
import java.sql.SQLException;

import Dao.Connector;
import Dao.UserDao;
import Model.Author;
import Model.Book;
import Model.Genre;

import java.sql.SQLException;
import java.util.*;

public class    BookService implements BookServiceInterface{
    private BookDao bookDao;
    private AuthorDao authorDao;

    public BookService()  {
        try {
            bookDao = new BookDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
            authorDao = new AuthorDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public List<Book> getBestBooks(int from, int to)  {
        BookDao bd = null;
        try {
            bd = new BookDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Book> bookList = bd.getAllBooks();
        List<Book> retList = new ArrayList<>();
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                if(o1.getBook_rating() == o2.getBook_rating())
                    return 0;
                if(o1.getBook_rating() > o2.getBook_rating())
                    return 1;
                else
                    return -1;
            }
        });
        for(int i = from; i<= to; i++){
            retList.add(bookList.get(i));
        }
        return retList;
    }

    @Override
    public List<Book> getLowRatingBooks(int from, int to) {
        List<Book> bookList = getBestBooks(from, to);
        Collections.reverse(bookList);
        return bookList;
    }


    @Override
    public Book getBookById(int id) throws SQLException {
        return bookDao.getBookById(id);
    }

    @Override
    public Author getAuthorById(int book_id) throws SQLException {
        return authorDao.getAuthor(book_id);
    }

    @Override
    public List<Book> availableBooks() {
        return bookDao.getAvailableBooks();
    }

    @Override
    public List<Book> getBooksByGanres(String[] genres, List<Book> list) {
        BookDao bd = null;
        try {
            bd = new BookDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Book> result = new ArrayList<Book>();
        for (int i = 0; i < genres.length; i++) {
            List<Book> tmp = bd.getBookByGenre(genres[i]);
            result.addAll(tmp);
        }
        result = removeDuplicates(result);
        return getIntersection(list, result);
    }

    private List<Book> getIntersection(List<Book> list, List<Book> tmp){
        List<Book> answer = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (contain(tmp, list.get(i))) {
                answer.add(list.get(i));
            }
        }
        return answer;
    }


    private List<Book> removeDuplicates(List<Book> list){
        List<Book> result = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(!result.contains(list.get(i))) result.add(list.get(i));
        }
        return result;
    }

    private boolean contain(List<Book> list, Book book){
        for(int i = 0; i < list.size(); i++){
            if(book.equals(list.get(i))) return true;
        }
        return false;
    }
}