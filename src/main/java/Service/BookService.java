package Service;

import Constants.SharedConstants;
import Dao.BookDao;
import java.sql.SQLException;

import Dao.UserDao;
import Model.Book;
import Model.Genre;

import java.sql.SQLException;
import java.util.*;

public class BookService implements BookServiceInterface{
    private BookDao bookDao;

    public BookService()  {
        bookDao = new BookDao(SharedConstants.DATA_BASE_NAME);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public List<Book> getBestBooks(int from, int to)  {
        BookDao bd = new BookDao(SharedConstants.DATA_BASE_NAME);
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

    public List<Book> oldToNew(List<Book> bookList) {
        BookDao bd = new BookDao(SharedConstants.DATA_BASE_NAME);
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                if(o1.getRelease_year() == o2.getRelease_year())
                    return 0;
                if(o1.getRelease_year() > o2.getRelease_year())
                    return 1;
                else
                    return -1;
            }
        });
        return bookList;
    }

    @Override
    public List<Book> newToOld(List<Book> bookList)  {
        List<Book> result = oldToNew(bookList);
        Collections.reverse(result);
        return result;
    }

    @Override
    public Book getBookById(int id) throws SQLException {
        return bookDao.getBookById(id);
    }

    @Override
    public List<Book> availableBooks() {
        return bookDao.getAvailableBooks();
    }

    @Override
    public List<Book> removeUnavailableBooks(List<Book> list){
        List<Book> result = new ArrayList<>();
        for(int i = 0 ; i < list.size(); i++){
            if(list.get(i).getAvailable_count() > 0){
                result.add(list.get(i));
            }
        }
        return result;
    }

    @Override
    public List<Book> getBooksByGanres(String[] genres){
        BookDao bd = new BookDao(SharedConstants.DATA_BASE_NAME);
        List<Book> result = new ArrayList<Book>();
        for(int i = 0; i < genres.length; i++){
            List<Book> tmp = bd.getBookByGenre(genres[i]);
            result.addAll(tmp);
        }
        return removeDuplicates(result);
    }

    private List<Book> removeDuplicates(List<Book> list){
        List<Book> result = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(!result.contains(list.get(i))) result.add(list.get(i));
        }
        return result;
    }

    private boolean contains(List<Book> list, Book book){
        for(int i = 0; i < list.size(); i++){
            if(book.equals(list.get(i))) return true;
        }
        return false;
    }
}