package Service;
import Dao.BookDao;
import Dao.BookDaoInterface;
import Model.Book;
import Constants.SharedConstants;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class bestBooks {
    public List<Book> getBestBooks(int from, int to) throws SQLException {
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

}
