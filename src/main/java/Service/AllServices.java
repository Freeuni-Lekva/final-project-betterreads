package Service;

import java.sql.SQLException;

public class AllServices {
    private HashService hashService;
    private UserService userService;
    private UserBooksService userBooksService;
    private VallidationService vallService;
    private DescriptionShortener descriptionShortener;
    private BookService bookService;
    private GenreService genreService;
    private AdminService adminService;
    private ReservationService reservationService;

    public AllServices() throws SQLException {
        hashService = new HashService();
        userService = new UserService();
        userBooksService = new UserBooksService();
        vallService = new VallidationService();
        descriptionShortener = new DescriptionShortener();
        bookService = new BookService();
        genreService = new GenreService();
        adminService = new AdminService();
        reservationService = new ReservationService();
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
    public AdminService getAdminService(){ return adminService; }
    public ReservationService getReservationService(){ return reservationService; }
}

