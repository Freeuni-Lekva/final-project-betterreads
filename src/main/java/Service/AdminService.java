package Service;

import Constants.SharedConstants;
import Dao.AdminDao;
import Dao.AuthorDao;
import Dao.Connector;
import Dao.GenreDao;
import Model.Genre;
import Model.User;

import java.sql.SQLException;

public class AdminService implements AdminServiceInterface{
    private AdminDao adminDao;
    private AuthorDao authorDao;
    private GenreDao genreDao;
    public AdminService(){
        try {
            adminDao = new AdminDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            authorDao = new AuthorDao(SharedConstants.DATA_BASE_NAME);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            genreDao = new GenreDao(Connector.getConnection(SharedConstants.DATA_BASE_NAME));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    public boolean isAdmin(User user) {
        return adminDao.isAdmin(user);
    }

    @Override
    public void addAuthor(String author_name) {
        authorDao.addAuthor(author_name);
    }

    @Override
    public void addGenre(String genre_name) {
        Genre genre = new Genre();
        genre.setGenre_name(genre_name);
        genreDao.addGenre(genre);
    }
}
