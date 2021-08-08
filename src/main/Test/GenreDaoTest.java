import Dao.CDB;
import Dao.Connector;
import Dao.GenreDao;
import Model.Genre;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GenreDaoTest extends TestCase {
    private Connection connection;
    private GenreDao genreDao;
    private CDB db;
    @Override
    protected void setUp() throws SQLException {
        db = new CDB();
        connection = db.getConnection();
        genreDao = new GenreDao(connection);
    }

    public void testGetGenre() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into genres(genre_name)" +
                " values (?);");
        preparedStatement.setString(1, "Dystopian");
        preparedStatement.execute();
        assertEquals(1, genreDao.getGenreById(1).getGenre_id());
        assertEquals("Dystopian", genreDao.getGenreById(1).getGenre_name());
    }

    public void testAddGenre() throws SQLException {
        Genre newGenre = new Genre();
        newGenre.setGenre_name("Historical");
        genreDao.addGenre(newGenre);
        PreparedStatement preparedStatement = connection.prepareStatement("select * from genres " +
                                                                                "where genre_name = ?");
        preparedStatement.setString(1, newGenre.getGenre_name());
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            assertEquals(newGenre.getGenre_name(), resultSet.getString("genre_name"));
        }
    }

    @Test
    public void testGetAll() throws SQLException {
        CDB newDb = new CDB();
        connection = newDb.getConnection();
        Set<String> genres = new TreeSet<>();
        genres.add("Historical");
        genres.add("Magical Realism");
        genres.add("Bildungsroman");

        for(String s: genres){
            PreparedStatement preparedStatement = connection.prepareStatement("insert into genres(genre_name) values(?);");
            preparedStatement.setString(1, s);
            preparedStatement.executeUpdate();
        }

        for(String i: genreDao.getAllGenres()){
            assertTrue(genres.contains(i));
            genres.remove(i);
        }
        assertEquals(0, genres.size());
    }
}
