package Service;

import Constants.SharedConstants;
import Dao.BookDao;
import Dao.Connector;
import Dao.GenreDao;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class GenreService implements GenreServiceInterface{


    private GenreDao genreDao;

    public GenreService() {
        try {
            genreDao = new GenreDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<String> getGenres(){
        return genreDao.getAllGenres();
    }
}
