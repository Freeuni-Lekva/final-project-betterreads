package Model;

public class Book {
    private int book_id;
    private String book_name;
    private String book_description;
    private int release_year;
    private int author_id;
    private double book_rating;

    public Book(){
    }
    public void setBook_id(int book_id){
        this.book_id = book_id;
    }
    public void setBook_name(String book_name){
        this.book_name = book_name;
    }
    public void setBook_description(String book_description){
        this.book_description = book_description;
    }
    public void setRelease_year(int release_year){
        this.release_year = release_year;
    }
    public void setAuthor_id(int author_id){
        this.author_id = author_id;
    }
    public void setBook_rating(double book_rating){
        this.book_rating = book_rating;
    }
    public int getBook_id(){
        return book_id;
    }
    public String getBook_name(){
        return book_name;
    }
    public String getBook_description(){
        return book_description;
    }
    public int getAuthor_id(){
        return author_id;
    }
    public int getRelease_year(){
        return release_year;
    }
    public double getBook_rating(){
        return book_rating;
    }
}
