import Dao.GenreDao;
import Model.Genre;
import Model.MockRow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenreDaoTests {
    @InjectMocks
    private GenreDao genreDao;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;
    @Captor
    ArgumentCaptor<Integer> id;

    @Before
    public void initTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdd() throws SQLException {
        Genre genre = new Genre();
        genre.setGenre_id(1);
        genre.setGenre_name("Horror");
        when(connection.prepareStatement(Mockito.anyString()))
                .thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        Assert.assertTrue(genreDao.addGenre(genre));
        Mockito.verify(connection, Mockito.times(1)).prepareStatement(anyString());
    }

    @Test
    public void testGetById() throws SQLException {
        Genre genre = new Genre();
        genre.setGenre_id(1);
        genre.setGenre_name("Horror");
        when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.first()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(genre.getGenre_id());
        when(resultSet.getString(2)).thenReturn(genre.getGenre_name());

        Assert.assertEquals(genre, genreDao.getGenreById(genre.getGenre_id()));
        Mockito.verify(statement).setInt(1, genre.getGenre_id());
    }

    @Test
    public void testGetMultipleGenres() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        Genre genreFantasy = new Genre();
        genreFantasy.setGenre_name("Fantasy");
        genreFantasy.setGenre_id(1);
        Genre genreSciFi = new Genre();
        genreSciFi.setGenre_id(2);
        genreSciFi.setGenre_name("Science fiction");
        Genre genreMystery = new Genre();
        genreMystery.setGenre_name("Mystery");
        genreMystery.setGenre_id(3);

        genres.add(genreFantasy);
        genres.add(genreSciFi);
        genres.add(genreMystery);

        when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        for(int i = 0; i < genres.size(); i++){
            when(resultSet.getInt(1)).thenReturn(genres.get(i).getGenre_id());
            when(resultSet.getString(2)).thenReturn(genres.get(i).getGenre_name());
            when(statement.executeQuery()).thenReturn(resultSet);
            when(resultSet.first()).thenReturn(true);

            Assert.assertEquals(genres.get(i), genreDao.getGenreById(i + 1));
            Mockito.verify(statement).setInt(anyInt(), eq(i+1));
        }

        when(resultSet.first()).thenReturn(false);
        Assert.assertEquals(null, genreDao.getGenreById(9));
        Mockito.verify(statement).setInt(anyInt(), eq(9));
    }

    @Test
    public void testGetAll() throws SQLException {
        Object[][] resultSetData = {{"Magical Realism"}, {"Historical"}, {"Horror"},
                {"Fantasy"}, {"Detective"}, {"Adventure"}, {"Dystopian"}};
        Set<String> allGenres = getGenreSetFromArray(resultSetData);
        setResultSetBehaviour(resultSetData);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        List<String> testAllGenres = genreDao.getAllGenres();
        Assert.assertEquals(allGenres.size(), testAllGenres.size());
        for(int i = 0; i < testAllGenres.size(); i++){
            Assert.assertTrue(allGenres.contains(testAllGenres.get(i)));
        }
    }

    private Set<String> getGenreSetFromArray(Object[][] arr){
        Set<String> res = new TreeSet<>();
        for(int i = 0; i < arr.length; i++){
            res.add((String)arr[i][0]);
        }
        return res;
    }

    private void setResultSetBehaviour(Object [][] result) throws SQLException {
        final AtomicInteger idx = new AtomicInteger(0);
        final MockRow row = new MockRow(result[0].length);

        doAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                int index = idx.getAndIncrement();
                if (result.length > index) {
                    row.setCurrentRowData(result[index]);
                    return true;
                } else
                    return false;
            }
        }).when(resultSet).next();

        doAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                int idx = (Integer) args[0];
                return row.getString(idx);
            }
        }).when(resultSet).getString(anyInt());

        doAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                String name = (String) args[0];
                return row.getString(name);
            }
        }).when(resultSet).getString(anyString());

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                String name = (String) args[0];
                return row.getObject(name);
            }
        }).when(resultSet).getObject(anyString());

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                int idx = (Integer) args[0];
                return row.getObject(idx);
            }
        }).when(resultSet).getObject(anyInt());

        doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                String name = (String) args[0];
                return row.getInt(name);
            }
        }).when(resultSet).getInt(anyString());

        doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                int idx = (Integer) args[0];
                return row.getInt(idx);
            }
        }).when(resultSet).getInt(anyInt());
    }
}
