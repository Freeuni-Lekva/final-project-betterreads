package Service;

import java.sql.SQLException;

public class AllServices {
    private HashService hashService;
    private UserService userService;
    private UserBooksService userBooksService;
    private VallidationService vallService;
    private DescriptionShortener descriptionShortener;
    private BookService bookService;

    public AllServices() throws SQLException {
        hashService = new HashService();
        userService = new UserService();
        userBooksService = new UserBooksService();
        vallService = new VallidationService();
        descriptionShortener = new DescriptionShortener();
        bookService = new BookService();
    }
    public HashService getHashService(){
        return hashService;
    }
    public UserService getUserService(){
        return userService;
    }
    public UserBooksService getUserBooksService(){ return userBooksService; }
    public VallidationService getVallService(){return vallService; }
    public DescriptionShortener getDescriptionShortener() {return descriptionShortener;}
    public BookService getBookService(){return bookService;}
}

