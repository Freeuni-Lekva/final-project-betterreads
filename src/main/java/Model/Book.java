package Model;

public class Book {
    private int book_id;
    private String book_name;
    private String book_description;
    private int release_year;
    private int author_id;
    private double book_rating;
    private int available_count ;

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
    public void setAvailable_count(int available_count ){this.available_count  = available_count ;}

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
    public int getAvailable_count(){return available_count ;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return book_name.equals(book.book_name) && author_id == book.author_id && release_year == book.release_year && book_description.equals(book.book_description);
    }
}
