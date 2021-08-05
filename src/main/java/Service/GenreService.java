package Service;

import Constants.SharedConstants;
import Dao.BookDao;
import Dao.GenreDao;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class GenreService {


    private GenreDao genreDao;

    public GenreService() throws SQLException {
        genreDao = new GenreDao(SharedConstants.DATA_BASE_NAME);
    }

    public List<String> getGenres(){
        System.out.println(423);
        for(int i = 0; i < genreDao.getAllGenres().size(); i++){
            System.out.println(genreDao.getAllGenres().get(i));
        }
        return genreDao.getAllGenres();
    }
}
