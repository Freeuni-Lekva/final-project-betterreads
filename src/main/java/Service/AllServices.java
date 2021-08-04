package Service;

public class AllServices {
    private HashService hashService;
    private UserService userService;
    private bestBooks bestBooks;
    private VallidationService vallService;
    public AllServices(){
        hashService = new HashService();
        userService = new UserService();
        bestBooks = new bestBooks();
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
    public VallidationService getVallService(){return vallService; }
}
