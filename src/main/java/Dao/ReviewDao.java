package Dao;

import Model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao implements ReviewDaoInterface{
    private final Connection conn;

    public ReviewDao(String dbName) throws SQLException {
        conn = Connector.getConnection(dbName);
    }

    @Override
    public void addReview(int user_id, int book_id, double rating, String comment, Date date, int num_likes) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO exam_lecturers  VALUES(?,?);");
            st.setInt(1, user_id);
            st.setInt(2, book_id);
            st.setDouble(3, rating);
            st.setString(4, comment);
            st.setDate(5, date);
            st.setInt(6, num_likes);
            if (st.executeUpdate() == 0)
                throw new SQLException("something went wrong while inserting a pair of exam and a lecturer");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Review> getReviews(int book_id) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM reviews WHERE book_id = ?;");
            st.setInt(1, book_id);
            ResultSet rs = st.executeQuery();
            List<Review> retList = new ArrayList<>();
            while(rs.next()){
                Review r = new Review();
                r.setUser_id(rs.getInt("user_id"));
                r.setBook_id(rs.getInt("book_id"));
                r.setRating(rs.getDouble("book_rating"));
                r.setComment(rs.getString("user_comment"));
                r.setDate(rs.getDate("date_posted"));
                r.setNum_likes(rs.getInt("num_likes"));
                retList.add(r);
            }
            return retList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
