package Model;

import java.sql.Date;
import java.util.Objects;

public class Review {
    private int review_id;
    private int user_id;
    private int book_id;
    private String comment;
    private Date date;
    private int num_likes;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public int getReview_id(){ return review_id; }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNum_likes() {
        return num_likes;
    }

    public void setNum_likes(int num_likes) {
        this.num_likes = num_likes;
    }

    public void setReview_id(int review_id){ this.review_id = review_id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return getUser_id() == review.getUser_id() && getBook_id() == review.getBook_id() && getDate().equals(review.getDate());
    }
}
