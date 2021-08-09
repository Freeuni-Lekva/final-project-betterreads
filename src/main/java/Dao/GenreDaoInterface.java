package Dao;

import Model.Genre;

import java.sql.SQLException;
import java.util.List;

public interface GenreDaoInterface {
    /**
     *
     * @param genre genre name
     */
    public boolean addGenre(Genre genre) throws SQLException;

    /**
     *
     * @param id genre id
     * @return Genre object with id = id
     */
    public Genre getGenreById(int id) throws SQLException;

    /**
     *
     * @return list of all genres
     */
    List<String> getAllGenres() throws SQLException;
}
