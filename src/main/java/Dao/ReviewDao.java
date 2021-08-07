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
    public void addReview(int user_id, int book_id, String comment, String date, int num_likes) throws SQLException {
        PreparedStatement st = conn.prepareStatement("INSERT INTO reviews " +
                "(user_id, book_id, user_comment, date_posted, num_likes)  VALUES(?,?,?,?,?);");
        st.setInt(1, user_id);
        st.setInt(2, book_id);

        st.setString(3, comment);
        st.setDate(4, Date.valueOf(date));
        st.setInt(5, num_likes);
        st.executeUpdate();
    }

    @Override
    public List<Review> getReviews(int book_id) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * FROM reviews WHERE book_id = ?" +
                " ORDER BY num_likes DESC;");
        st.setInt(1, book_id);
        ResultSet rs = st.executeQuery();
        List<Review> retList = new ArrayList<>();
        while(rs.next()){
            Review r = new Review();
            r.setReview_id(rs.getInt("review_id"));
            r.setUser_id(rs.getInt("user_id"));
            r.setBook_id(rs.getInt("book_id"));
            r.setComment(rs.getString("user_comment"));
            r.setDate(rs.getDate("date_posted"));
            r.setNum_likes(rs.getInt("num_likes"));
            retList.add(r);
        }
        return retList;
    }

    public List<Review> getReviewsByUserId(int book_id, int user_id) throws SQLException {
        List<Review> res = new ArrayList<>();

        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM reviews WHERE book_id = ?" +
                " AND user_id = ?" +
                " ORDER BY num_likes DESC;");
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

        return res;
    }

    @Override
    public boolean alreadyLiked(int user_id, int review_id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("select * from likes where " +
                "user_id = ? and review_id = ?;");
        preparedStatement.setInt(1, user_id);
        preparedStatement.setInt(2, review_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    @Override
    public int getNumLikesById(int review_id) throws SQLException {
        int res = 0;
        PreparedStatement preparedStatement = conn.prepareStatement("select num_likes from reviews " +
                "where review_id = ?;");
        preparedStatement.setInt(1, review_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            res = resultSet.getInt("num_likes");
        }

        return res;
    }

    @Override
    public boolean likeReview(int user_id, int review_id) throws SQLException {
        if(alreadyLiked(user_id, review_id)) return false;
        PreparedStatement preparedStatement = conn.prepareStatement("insert into likes(user_id, review_id) " +
                "values(?, ?);");
        preparedStatement.setInt(1, user_id);
        preparedStatement.setInt(2, review_id);
        preparedStatement.execute(); //== 0) return false;

        int num_likes = getNumLikesById(review_id);
        num_likes++;
        return updateNumLikes(review_id, num_likes);
    }

    private boolean updateNumLikes(int review_id, int num_likes) throws SQLException {
        PreparedStatement updateStatement = conn.prepareStatement("update reviews set num_likes = ? " +
                "where review_id = ?; ");
        updateStatement.setInt(1, num_likes);
        updateStatement.setInt(2, review_id);
        return updateStatement.executeUpdate() != 0;
    }

    @Override
    public boolean unlikeReview(int user_id, int review_id) throws SQLException {
        if(!alreadyLiked(user_id, review_id)) return false;

        PreparedStatement deleteFromLikes = conn.prepareStatement("delete from likes where " +
                "user_id = ? and review_id = ?;");
        deleteFromLikes.setInt(1, user_id);
        deleteFromLikes.setInt(2,review_id);
        if(deleteFromLikes.executeUpdate() == 0) return false;

        int num_likes = getNumLikesById(review_id);
        num_likes--;
        return updateNumLikes(review_id, num_likes);
    }

    @Override
    public boolean deleteReview(int review_id) throws SQLException {
        PreparedStatement deleteLikes = conn.prepareStatement("delete from likes " +
                "where review_id = ?;");
        deleteLikes.setInt(1, review_id);
        deleteLikes.executeUpdate();

        PreparedStatement deleteReview = conn.prepareStatement("delete from reviews " +
                "where review_id = ?;");
        deleteReview.setInt(1, review_id);
        return deleteReview.executeUpdate() != 0;
    }
}
