package Dao;

import Model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao implements ReviewDaoInterface{
    private final Connection conn;

    public ReviewDao(Connection connection) {
        this.conn = connection;
    }

    @Override
    public void addReview(int user_id, int book_id, String comment, String date, int num_likes) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO reviews " +
                    "(user_id, book_id, user_comment, date_posted, num_likes)  VALUES(?,?,?,?,?);");
            st.setInt(1, user_id);
            st.setInt(2, book_id);

            st.setString(3, comment);
            st.setDate(4, Date.valueOf(date));
            st.setInt(5, num_likes);
            if (st.executeUpdate() == 0)
                throw new SQLException();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Review> getReviews(int book_id) {
//        try {
//            PreparedStatement st = conn.prepareStatement("SELECT * FROM reviews WHERE book_id = ?;");
//            st.setInt(1, book_id);
//            ResultSet rs = st.executeQuery();
//            List<Review> retList = new ArrayList<>();
//            while(rs.next()){
//                Review r = new Review();
//                r.setUser_id(rs.getInt("user_id"));
//                r.setBook_id(rs.getInt("book_id"));
//                r.setComment(rs.getString("user_comment"));
//                r.setDate(rs.getDate("date_posted"));
//                r.setNum_likes(rs.getInt("num_likes"));
//                retList.add(r);
//            }
//            return retList;
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        return null;
    }

    public List<Review> getReviewsByUserId(int book_id, int user_id){
        List<Review> res = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM reviews WHERE book_id = ?" +
                    " AND user_id = ?;");
            preparedStatement.setInt(1, book_id);
            preparedStatement.setInt(2, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Review r = new Review();
                r.setReview_id(resultSet.getInt("review_id"));
                r.setComment(resultSet.getString("user_comment"));
                r.setDate(resultSet.getDate("date_posted"));
                r.setUser_id(resultSet.getInt("user_id"));
                r.setBook_id(resultSet.getInt("book_id"));
                r.setNum_likes(resultSet.getInt("num_likes"));
                res.add(r);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }
}
