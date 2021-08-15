package Service;

import Constants.SharedConstants;
import Dao.BookDao;
import Dao.Connector;
import Model.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookServiceSort implements BookServiceSortInterface {

    public List<Book> oldToNew(List<Book> bookList) throws SQLException {
        BookDao bd = new BookDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
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

    public List<Book> newToOld(List<Book> bookList) throws SQLException {
        List<Book> result = oldToNew(bookList);
        Collections.reverse(result);
        return result;
    }

    public  List<Book> sortLowToHigh(List<Book> bookList){
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
        return bookList;
    }

    public List<Book> sortHighToLow(List<Book> bookList){
        List<Book> result = sortLowToHigh(bookList);
        Collections.reverse(result);
        return result;
    }

    public List<Book> removeUnavailableBooks(List<Book> list){
        List<Book> result = new ArrayList<>();
        for(int i = 0 ; i < list.size(); i++){
            if(list.get(i).getAvailable_count() > 0){
                result.add(list.get(i));
            }
        }
        return result;
    }
}
