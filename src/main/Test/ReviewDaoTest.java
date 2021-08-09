import Dao.CDB;
import Dao.Connector;
import Dao.ReviewDao;
import Model.Review;
import junit.framework.TestCase;
import org.junit.Assert;

import java.sql.*;
import java.text.ParseException;
import java.util.List;

public class ReviewDaoTest extends TestCase{
    Connection connection;
    ReviewDao rd;

    protected void setUp() throws SQLException {
        CDB db = new CDB();
        this.connection = db.getConnection();
        this.rd = new ReviewDao(connection);
    }

    private void helper() throws SQLException {
        PreparedStatement ps1 = connection.prepareStatement
                ("insert into users (first_name,last_name, email, username, password_hash) values ('Tamo', 'Mikeladze', 'tmike19@freeuni.edu.ge', 'tmike19', '1234');");
        ps1.execute();

        PreparedStatement ps2 = connection.prepareStatement
                ("insert into users (first_name,last_name, email, username, password_hash) " +
                        "values ('Tamo2', 'Mikeladze2', 'tmike19@freeuni.edu.ge2', 'tmike192' ,'12342');");
        ps2.execute();

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

        PreparedStatement ps3 = connection.prepareStatement("insert into books(book_name, book_description, release_year, author_id, book_rating, available_count)\n" +
                "           values(\"War and Peace\", \"In Russia's struggle with Napoleon, Tolstoy saw a tragedy that involved all mankind.\n" +
                "                 Greater than a historical chronicle, War and Peace is an affirmation of life itself, `a complete picture', \n" +
                "                 as a contemporary reviewer put it, `of everything in which people find their happiness and greatness, their \n" +
                "                 grief and humiliation'. Tolstoy gave his personal approval to this translation, published here in a new single  \n" +
                "                 volume edition, which includes an introduction by Henry Gifford, and Tolstoy's important essay `Some Words about\n" +
                "                 War and Peace'.\", \n" +
                "           1867,  2, 0, 3);");
        ps3.execute();

        PreparedStatement ps4 = connection.prepareStatement("insert into books(book_name, book_description, release_year, author_id, book_rating, available_count)\n" +
                "           values(\"The Man Who Laughs\", \"It starts on the night of January 29, 1690, a ten-year-old boy abandoned -- the stern men \n" +
                "                  who've kept him since infancy have wearied of him. The boy wanders, barefoot and starving, through a snowstorm to\n" +
                "                  reach a gibbet bearing the corpse of a hanged criminal. Beneath the gibbet is a ragged woman, frozen to death. The \n" +
                "                  boy is about to move onward when he hears a sound within the woman's garments: He discovers an infant girl, barely\n" +
                "                  alive, clutching the woman's breast. A single drop of frozen milk, resembling a pearl, is on the woman's lifeless breast...\", \n" +
                "           1869,  5, 0, 2);");
        ps4.execute();
    }

    public void reviewHelper() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into reviews(user_id, " +
                " book_id, user_comment, date_posted, num_likes) " +
                "values(?, ?, ?, ?, ?);");
        preparedStatement.setInt(1, 1);
        preparedStatement.setInt(2,2);
        preparedStatement.setString(3, "vauuuuuuuu");
        preparedStatement.setDate(4, Date.valueOf("2021-07-09"));
        preparedStatement.setInt(5, 2);
        preparedStatement.executeUpdate();
    }

    public void testAddReview() throws SQLException, ParseException {
        helper();

        rd.addReview(1, 2, "vauuuuuuuu", "2021-07-09", 2);
        PreparedStatement s1 = connection.prepareStatement
                ("select * from reviews where user_comment = 'vauuuuuuuu';");
        ResultSet rs = s1.executeQuery();
        assertTrue(rs.next());
        PreparedStatement reviewByBook = connection.prepareStatement("select * from reviews where book_id = 2;");
        ResultSet resultSet = reviewByBook.executeQuery();
        assertTrue(resultSet.next());
        assertEquals(1, resultSet.getInt("user_id"));
        assertEquals("vauuuuuuuu", resultSet.getString("user_comment"));
        assertEquals(2, resultSet.getInt("num_likes"));
    }

    public void testGetReview() throws SQLException {
        CDB newDb = new CDB();
        connection = newDb.getConnection();
        helper();
        reviewHelper();
        List<Review> lst = rd.getReviews(2);
        Assert.assertEquals(1, lst.size());
        assertEquals(1, lst.get(0).getUser_id());
        assertEquals("vauuuuuuuu", lst.get(0).getComment());
    }

    public void testGetByUser() throws SQLException {
        CDB newDb = new CDB();
        connection = newDb.getConnection();
        helper();
        reviewHelper();
        List<Review> lst = rd.getReviewsByUserId(2, 1);
        Assert.assertEquals(1, lst.size());
        assertEquals(1, lst.get(0).getUser_id());
        assertEquals("vauuuuuuuu", lst.get(0).getComment());
    }

    public void testLike() throws SQLException {
        CDB newDb = new CDB();
        connection = newDb.getConnection();
        helper();
        reviewHelper();
        rd.likeReview(1, 1);
        assertTrue(rd.alreadyLiked(1,1));
        rd.unlikeReview(1, 1);
        assertFalse(rd.alreadyLiked(1,1));
    }

    public void testDeleteReview() throws SQLException {
        CDB newDb = new CDB();
        connection = newDb.getConnection();
        helper();
        reviewHelper();
        rd.deleteReview(1);
        assertEquals(0, rd.getReviews(2).size());
        assertEquals(0, rd.getReviewsByUserId(2, 1).size());
        assertEquals(0, rd.getNumLikesById(1));
    }
}
