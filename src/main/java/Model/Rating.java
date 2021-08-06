package Model;

public class Rating {
    private int rating_id;
    private int user_id;
    private int book_id;
    private int book_rating;

    public Rating() {};

    public int getBook_id() {
        return book_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getBook_rating() {
        return book_rating;
    }

    public int getRating_id() {
        return rating_id;
    }

    public void setBook_rating(int book_rating) {
        this.book_rating = book_rating;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public void setRating_id(int rating_id) {
        this.rating_id = rating_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
