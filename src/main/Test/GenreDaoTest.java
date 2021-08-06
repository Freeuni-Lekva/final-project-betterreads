import Dao.Connector;
import Dao.GenreDao;
import Model.Genre;
import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreDaoTest extends TestCase {
    private Connection connection;
    private GenreDao genreDao;

    @Override
    protected void setUp() throws SQLException {
        connection = Connector.getConnection("testLibrary");
        genreDao = new GenreDao(connection);
    }

    public void testGetGenre() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into genres(genre_name)" +
                " values (?);");
        preparedStatement.setString(1, "horori");
        preparedStatement.execute();
        assertEquals(1, genreDao.getGenreById(1).getGenre_id());
        assertEquals("horori", genreDao.getGenreById(1).getGenre_name());
    }

    public void testAddGenre() throws SQLException {
        Genre newGenre = new Genre();
        newGenre.setGenre_name("varchar2");
        genreDao.addGenre(newGenre);
        PreparedStatement preparedStatement = connection.prepareStatement("select * from genres " +
                                                                                "where genre_name = ?");
        preparedStatement.setString(1, "varchar2");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            assertEquals("varchar2", resultSet.getString("genre_name"));
        }
    }
}
