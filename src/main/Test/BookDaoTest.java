import Dao.BookDao;
import Dao.CDB;
import Dao.Connector;
import Model.Book;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BookDaoTest extends TestCase {

    private String dbName;
    private Connection connection;

    private void helperAddBookGenres(){
        try {
            PreparedStatement p1 = connection.prepareStatement("insert into book_genres(book_id, genre_id) " +
                    "values(1, 1)");
            p1.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void helperAddManyBooks(){
        try {
            PreparedStatement ps5 = connection.prepareStatement("insert into authors (author_name) " +
                    "values('Fyodor Dostoevsky');");
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

            PreparedStatement p10 = connection.prepareStatement("insert into books(book_name, book_description, release_year, author_id, book_rating, available_count)\n" +
                    "\t\t\tvalues(\"Crime and Punishment\",\"Crime and Punishment focuses on the mental anguish and moral dilemmas\n" +
                    "\t\t\t\t\tof Rodion Raskolnikov, an impoverished ex-student in Saint Petersburg who formulates a plan \n" +
                    "                    to kill an unscrupulous pawnbroker for her money\",\n" +
                    "                    1866,1,0,0);");
            p10.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void helperAddBookAndAuthor(){
        try {
            PreparedStatement statement1 = connection.prepareStatement("insert into authors (author_name) " +
                    "values('Shota Rustaveli');");
            statement1.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            PreparedStatement statement2 = connection.prepareStatement("insert into books " +
                    "(book_name, book_description, release_year, author_id, book_rating, available_count, book_photo) " +
                    "values (?, ?, ?, ?, ?, ?, ?);");
            statement2.setString(1,"book1");
            statement2.setString(2, "book1_description");
            statement2.setInt(3, 1);
            statement2.setInt(4, 1);
            statement2.setInt(5, 0);
            statement2.setInt(6, 1);
            statement2.setString(7, "");
            statement2.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void setUp() throws SQLException {
        CDB cdb = new CDB();
        this.dbName = "testLibrary";
        Connection connection = Connector.getConnection(dbName);
        this.connection = connection;
    }

    public void testGetBookById(){
        helperAddBookAndAuthor();
        BookDao bd = null;
        try {
            bd = new BookDao(Connector.getConnection("testDataBase"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Book res = null;
        try {
            res = bd.getBookById(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Book expected = new Book();
        expected.setBook_rating(0);
        expected.setBook_name("book1");
        expected.setBook_description("book1_description");
        expected.setBook_id(1);
        expected.setAuthor_id(1);
        expected.setRelease_year(1);
        expected.setBook_photo("");
        assertEquals(expected, res);
    }

    public void testSetGetBookCount(){
        helperAddBookAndAuthor();
        BookDao bd = null;
        try {
            bd = new BookDao(Connector.getConnection("testDataBase"));
            assertEquals(1,bd.getBookCount(1));
            bd.setBookCount(1, 5);
            assertEquals(5,bd.getBookCount(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void testGetBookByGenre(){
        helperAddManyBooks();
        helperAddBookGenres();
        BookDao bd = null;
        try {
            bd = new BookDao(Connector.getConnection("testDataBase"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Book> lst = null;
        try {
            lst = bd.getBookByGenre("Mystery");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(1, lst.size());
    }

    public void testGetAvailableBooks(){
        helperAddManyBooks();
        BookDao bd = null;
        try {
            bd = new BookDao(Connector.getConnection("testDataBase"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Book> lst = null;
        try {
            lst = bd.getAvailableBooks();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(2, lst.size());
    }

    public void testGetAllBooks(){
        helperAddManyBooks();
        BookDao bd = null;
        try {
            bd = new BookDao(Connector.getConnection("testDataBase"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Book> books = null;
        try {
            books = bd.getAllBooks();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(3, books.size());
    }

}
