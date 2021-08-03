package Service;

public class AllServices {
    private HashService hashService;
    private UserService userService;
    public AllServices(){
        hashService = new HashService();
        userService = new UserService();
    }
    public HashService getHashService(){
        return hashService;
    }
    public UserService getUserService(){
        return userService;
    }
}
