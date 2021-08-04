package Service;

import java.sql.SQLException;

public class AllServices {
    private HashService hashService;
    private UserService userService;
    private bestBooks bestBooks;
    private UserBooksService userBooksService;
    private VallidationService vallService;
    private DescriptionShortener descriptionShortener;
    private BookService bookService;
    public AllServices() throws SQLException {
        hashService = new HashService();
        userService = new UserService();
        bestBooks = new bestBooks();
        userBooksService = new UserBooksService();
        vallService = new VallidationService();
        descriptionShortener = new DescriptionShortener();
        bookService = new BookService();
    }

    public BookService getBookService(){
        return bookService;
    }
    public HashService getHashService(){
        return hashService;
    }
    public UserService getUserService(){
        return userService;
    }
    public Service.bestBooks getBestBooks() {
        return bestBooks;
    }
    public UserBooksService getUserBooksService(){ return userBooksService; }
    public VallidationService getVallService(){return vallService; }
    public DescriptionShortener getDescriptionShortener() {return descriptionShortener;}
}