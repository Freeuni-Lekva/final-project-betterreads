package Service;

public class AllServices {
    private HashService hashService;
    private UserService userService;
    private bestBooks bestBooks;
    private UserBooksService userBooksService;
    private VallidationService vallService;
    public AllServices(){
        hashService = new HashService();
        userService = new UserService();
        bestBooks = new bestBooks();
        userBooksService = new UserBooksService();
        vallService = new VallidationService();
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
}
