package Service;

import Constants.SharedConstants;
import Dao.AuthorDao;
import Dao.BookDao;
import Dao.Connector;
import Model.Book;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchService implements SearchServiceInterface {

//    public
    @Override
    public List<Book> filterByText(String text){
        try {
            BookDao bd = new BookDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
            AuthorDao ad = new AuthorDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
            List<Book> bookList = bd.getAllBooks();
            Collections.sort(bookList, new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    double d1 = (double)levDistance(o1.getBook_name(), text) / o1.getBook_name().length();
                    double d2 = (double)levDistance(o2.getBook_name(), text) / o2.getBook_name().length();
                    String a1 = null;
                    String a2 = null;
                    try {
                        a1 = ad.getAuthor(o1.getBook_id()).getAuthor_name();
                        a2 = ad.getAuthor(o2.getBook_id()).getAuthor_name();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    double d3 = (double)levDistance(a1, text) / a1.length();
                    double d4 = (double)levDistance(a2, text) / a2.length();
                    double m1 = Math.min(d1, d3);
                    double m2 = Math.min(d2, d4);
                    if (m1 == m2)
                        return 0;
                    else if (m1 > m2)
                        return 1;
                    else
                        return -1;
                }
            });
            return bookList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int levDistance(String s1, String s2){
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for(int i = 0; i <= s1.length(); i++){
            for(int j = 0; j <= s2.length(); j++){
                if(i == 0){
                    dp[i][j] = j;
                    continue;
                }
                if(j == 0){
                    dp[i][j] = i;
                    continue;
                }
                char c1 = Character.toLowerCase(s1.charAt(i-1));
                char c2 = Character.toLowerCase(s2.charAt(j-1));
                int add = 0;
                if(c1 != c2) add++;
                dp[i][j] = Math.min(dp[i - 1][j - 1] + add, Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
            }
        }

        return dp[s1.length()][s2.length()];
    }
}
