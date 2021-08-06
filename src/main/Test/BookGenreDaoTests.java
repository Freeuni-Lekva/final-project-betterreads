import Dao.BookGenreDao;
import Model.Genre;
import Model.MockRow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BookGenreDaoTests {
    @InjectMocks
    private BookGenreDao bookGenreDao;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    Object [][] resultSetData = {{1, 1, 1, "Horror"},
                                {1, 3, 3, "Historical"},
                                {2, 2, 2, "Fantasy"},
                                {3, 3, 3, "Historical"},
                                {3, 5, 5, "Detective"},
                                {4, 1, 1, "Horror"},
                                {5, 2, 2, "Fantasy"},
                                {5, 4, 4, "Adventure"},
                                {5, 6, 6, "Romance"},
                                {6, 7, 7, "Dystopian"},
                                {7, 8, 8, "Magical Realism"}};

    @Before
    public void initTest(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetGenresEmpty() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        Assert.assertEquals(0, bookGenreDao.getBookGenres(1).size());
    }

    @Test
    public void testGetGenres() throws SQLException {
        Object[][] testData = new Object[3][4];
        testData[0] = resultSetData[6];
        testData[1] = resultSetData[7];
        testData[2] = resultSetData[8];
        Set<Genre> expected = getGenreSetFromArray(testData);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        setResultSetBehaviour(testData);
        int bookId = 5;
        List<Genre> actual = bookGenreDao.getBookGenres(bookId);
        verify(preparedStatement).setInt(anyInt(), eq(bookId));

        Assert.assertEquals(expected.size(), actual.size());

        for(int i = 0; i < actual.size(); i++){
            Assert.assertTrue(expected.contains(actual.get(i)));
        }
    }

    @Test
    public void testAddAll() throws SQLException {
        List<Integer> genres = new ArrayList<>();
        Genre genre = new Genre();
        genre.setGenre_name("Historical");
        genre.setGenre_id(3);
        genres.add(genre.getGenre_id());
        genre.setGenre_name("Adventure");
        genre.setGenre_id(4);
        genres.add(genre.getGenre_id());
        int bookId = 2;
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        Assert.assertTrue(bookGenreDao.addBookGenres(bookId, genres));
        verify(preparedStatement).setInt(eq(bookId), eq(3));
        verify(preparedStatement).setInt(eq(bookId), eq(4));
        verify(preparedStatement, times(genres.size())).executeUpdate();
    }

    @Test
    public void testNonExistentBook() throws SQLException {
        Set<Integer> bookIds = new HashSet<>();
        Set<Integer> genreIds = new HashSet<>();
        bookIds.add(1);
        bookIds.add(2);
        bookIds.add(3);
        genreIds.add(5);
        genreIds.add(7);
        genreIds.add(2);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws SQLException {
                int paramInd = (Integer) invocation.getArguments()[0];
                int param = (Integer) invocation.getArguments()[1];
                if (paramInd == 1) {
                    if(!(bookIds.contains(param))){
                        doThrow(new SQLException());
                    }
                } else {
                    if(!genreIds.contains(param))
                        doThrow(new SQLException());
                }
                return null;
            }
        }).when(preparedStatement).setInt(anyInt(), anyInt());

        when(preparedStatement.executeUpdate()).thenReturn(1);
        List<Integer> genresTest = new ArrayList<>();
        genresTest.add(5);
        genresTest.add(7);
        Assertions.assertTrue(bookGenreDao.addBookGenres(1, genresTest));
        genresTest.add(1);
        //Assertions.assertThrows(SQLException.class, () -> bookGenreDao.addBookGenres(8, genresTest));
    }

    private Set<Genre> getGenreSetFromArray(Object[][] arr){
        Set<Genre> res = new TreeSet<>();
        for(int i = 0; i < arr.length; i++){
            Genre genre = new Genre();
            genre.setGenre_id((Integer) arr[i][2]);
            genre.setGenre_name((String) arr[i][3]);
            res.add(genre);
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
