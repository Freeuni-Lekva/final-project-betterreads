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
    private ReservationService reservationService;
    private SearchService searchService;
    private SuggestionService suggestionService;
    private RatingService ratingService;
    private SendMailService mailService;

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
        reservationService = new ReservationService();
        searchService = new SearchService();
        suggestionService = new SuggestionService();
        ratingService = new RatingService();
        mailService = new SendMailService();

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
    public ReservationService getReservationService(){ return reservationService; }

    public SearchService getSearchService() {return searchService;}
    public SuggestionService getSuggestionService() {return suggestionService;}
    public RatingService getRatingService() {return ratingService;}
    public SendMailService getMailService(){ return mailService; }
}

