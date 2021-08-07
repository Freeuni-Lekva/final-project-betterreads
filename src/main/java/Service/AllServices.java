package Service;

import java.sql.SQLException;

public class AllServices {
    private HashService hashService;
    private UserService userService;
    private UserBooksService userBooksService;
    private VallidationService vallService;
    private DescriptionShortener descriptionShortener;
    private BookService bookService;
    public GenreService genreService;
    private ReviewService reviewService;
    private AdminService adminService;

    public AllServices() throws SQLException {
        hashService = new HashService();
        userService = new UserService();
        userBooksService = new UserBooksService();
        vallService = new VallidationService();
        descriptionShortener = new DescriptionShortener();
        bookService = new BookService();
        genreService = new GenreService();
        reviewService = new ReviewService();
        adminService = new AdminService();

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
    public GenreService getGenreService(){return genreService;}

    public ReviewService getReviewService() { return reviewService; }

    public AdminService getAdminService(){ return adminService; }

}

