import Dao.BookGenreDao;
import Dao.CDB;
import Model.Genre;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookGenreDaoTest extends TestCase {
    Connection connection;
    BookGenreDao bgd;
    List<String> genres;
    protected void setUp() throws SQLException {
        CDB db = new CDB();
        genres = new ArrayList<>();
        this.connection = db.getConnection();
        bgd = new BookGenreDao(connection);
    }

    private void helper() throws SQLException {
        genres.clear();
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
        genres.add("Mystery");
        PreparedStatement p4 = connection.prepareStatement("insert into genres (genre_name)\n" +
                "           values(\"Thriller\"); ");
        p4.execute();
        genres.add("Thriller");
        PreparedStatement p5 = connection.prepareStatement("insert into genres(genre_name)\n" +
                "           values(\"Horror\");");
        p5.execute();
        genres.add("Horror");
        PreparedStatement p6 = connection.prepareStatement("insert into genres(genre_name)\n" +
                "           values(\"Historical\");");
        p6.execute();
        genres.add("Historical");
        PreparedStatement p7 = connection.prepareStatement("insert into genres(genre_name)\n" +
                "           values(\"Romance\"); ");
        p7.execute();
        genres.add("Romance");
    }

    public void testAddBookGenres() throws SQLException {
        helper();
        List<Integer> testGenres = new ArrayList<>();
        testGenres.add(1);
        testGenres.add(2);
        bgd.addBookGenres(1, testGenres);

        PreparedStatement p1 = connection.prepareStatement("select * from book_genres where book_id = 1;");
        ResultSet rs = p1.executeQuery();
        while(rs.next()){
            int curr = rs.getInt("genre_id");
            assertTrue(testGenres.contains(curr));
            testGenres.remove((Integer)curr);
        }
        assertEquals(0, testGenres.size());
    }

    public void testAdd() throws SQLException {
        CDB db = new CDB();
        connection = db.getConnection();
        helper();
        List<Integer> testGenres = new ArrayList<>();
        testGenres.add(2);
        testGenres.add(3);
        testGenres.add(4);
        bgd.addBookGenres(2, testGenres);

        PreparedStatement p1 = connection.prepareStatement("select * from book_genres where book_id = 2;");
        ResultSet rs = p1.executeQuery();

        while(rs.next()){
            int curr = rs.getInt("genre_id");
            assertTrue(testGenres.contains(curr));
            testGenres.remove((Integer)curr);
        }
        assertEquals(0, testGenres.size());
    }

    public void testEmpty() throws SQLException {
        CDB db = new CDB();
        connection = db.getConnection();
        helper();
        PreparedStatement p1 = connection.prepareStatement("select * from book_genres where book_id = 10;");
        ResultSet rs = p1.executeQuery();
        assertFalse(rs.next());
    }

    public void testGetBookGenres() throws SQLException {
        CDB db = new CDB();
        connection = db.getConnection();
        helper();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into book_genres(book_id, genre_id) values (1, 1);");
        preparedStatement.executeUpdate();
        PreparedStatement addSecondGenre = connection.prepareStatement("insert into book_genres(book_id, genre_id) values (1, 3);");
        addSecondGenre.executeUpdate();
        Set<Integer> testedGenres = new HashSet<>();
        testedGenres.add(1);
        testedGenres.add(3);
        List<Genre> lst = bgd.getBookGenres(1);
        assertEquals(testedGenres.size(), lst.size());
        for(Genre i: lst){
            assertTrue(testedGenres.contains(i.getGenre_id()));
            testedGenres.remove(i.getGenre_id());
        }
        assertEquals(0, testedGenres.size());
    }
}
