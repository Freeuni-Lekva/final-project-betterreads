import Dao.BookGenreDao;
import Dao.Connector;
import Dao.ReviewDao;
import Model.Genre;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import junit.framework.TestCase;
import org.junit.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookGenreDaoTest extends TestCase {
    Connection connection;
    BookGenreDao bgd;

    protected void setUp() throws SQLException {
        bgd = new BookGenreDao("testLibrary");
        connection = Connector.getConnection("testLibrary");
    }

    private void helper() throws SQLException {
        PreparedStatement ps5 = connection.prepareStatement("insert into authors (author_name) values('Fyodor Dostoevsky');");
        ps5.execute();

        PreparedStatement ps9 = connection.prepareStatement
                ("insert into authors(author_name) values('Leo Tolstoy'); ");
        ps9.execute();

        PreparedStatement ps6 = connection.prepareStatement("insert into authors(author_name) values('William Shakespeare'); ");
        ps6.execute();

        PreparedStatement ps7 = connection.prepareStatement("insert into authors(author_name) values('Charles Dickens');");
        ps7.execute();

        PreparedStatement ps8 = connection.prepareStatement("insert into authors(author_name) values('Victor Hugo'); ");
        ps8.execute();
        PreparedStatement p1 = connection.prepareStatement("insert into books(book_name, book_description, release_year, author_id, book_rating, available_count)\n" +
                "           values(\"The Idiot\", \"The Idiot, novel by Fyodor Dostoyevsky, published in Russian as Idiot in\n" +
                "                 in 1868�69. The narrative concerns the unsettling effect of the �primitive� Prince Myshkin on the \n" +
                "                 sophisticated, conservative Yepanchin family and their friends. Myshkin visits the Yepanchins, and\n" +
                "                 his odd manner and lack of concern for appearances quickly make him an object of fascination.\n" +
                "                 His hosts, who are given to sensuality, acquisitiveness, and crime, test his moral compass. Myshkin \n" +
                "                 maintains a guileless benevolence toward all, but, though his faith and radiant personality\n" +
                "                 draw them to him, his message of service, compassion, and brotherly love finally fails.\", \n" +
                "           1869,  1, 0, 4);");
        p1.execute();

        PreparedStatement p2 = connection.prepareStatement("insert into books(book_name, book_description, release_year, author_id, book_rating, available_count)\n" +
                "           values(\"War and Peace\", \"In Russia's struggle with Napoleon, Tolstoy saw a tragedy that involved all mankind.\n" +
                "                 Greater than a historical chronicle, War and Peace is an affirmation of life itself, `a complete picture', \n" +
                "                 as a contemporary reviewer put it, `of everything in which people find their happiness and greatness, their \n" +
                "                 grief and humiliation'. Tolstoy gave his personal approval to this translation, published here in a new single  \n" +
                "                 volume edition, which includes an introduction by Henry Gifford, and Tolstoy's important essay `Some Words about\n" +
                "                 War and Peace'.\", \n" +
                "           1867,  2, 0, 3);");
        p2.execute();

        PreparedStatement p3 = connection.prepareStatement("insert into genres(genre_name)\n" +
                "           values(\"Mystery\"); ");
        p3.execute();

        PreparedStatement p4 = connection.prepareStatement("insert into genres (genre_name)\n" +
                "           values(\"Thriller\"); ");
        p4.execute();

        PreparedStatement p5 = connection.prepareStatement("insert into genres(genre_name)\n" +
                "           values(\"Horror\");");
        p5.execute();

        PreparedStatement p6 = connection.prepareStatement("insert into genres(genre_name)\n" +
                "           values(\"Historical\");");
        p6.execute();

        PreparedStatement p7 = connection.prepareStatement("insert into genres(genre_name)\n" +
                "           values(\"Romance\"); ");
        p7.execute();
    }

    public void testAddBookGenres() throws SQLException {
        helper();
        List<Integer> genres = new ArrayList<>();
        genres.add(1);
        genres.add(2);
        bgd.addBookGenres(1, genres);

        genres.clear();
        genres.add(2);
        genres.add(3);
        genres.add(4);
        bgd.addBookGenres(2, genres);

        PreparedStatement p1 = connection.prepareStatement("select * from book_genres where book_id = 1;");
        ResultSet rs = p1.executeQuery();
        Assert.assertTrue(rs.next());

        PreparedStatement p2 = connection.prepareStatement("select * from book_genres where genre_id = 2;");
        rs = p2.executeQuery();
        Assert.assertTrue(rs.next());

        PreparedStatement p3 = connection.prepareStatement("select * from book_genres where genre_id = 10;");
        rs = p3.executeQuery();
        Assert.assertFalse(rs.next());

    }

    public void testGetBookGenres() throws SQLException {
        List<Genre> lst = bgd.getBookGenres(1);
        assertEquals(2, lst.size());
        lst = bgd.getBookGenres(2);
        assertEquals(3, lst.size());
    }

}
