package Model;

public class User {
    private int user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String username;
    private String password_hash;

    public User(){
    }
    public void setUser_id(int user_id){
        this.user_id = user_id;
    }
    public void setFirst_name(String first_name){
        this.first_name = first_name;
    }
    public void setLast_name(String last_name){
        this.last_name = last_name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword_hash(String password_hash){
        this.password_hash = password_hash;
    }
    public int getUser_id(){
        return user_id;
    }
    public String getFirst_name(){
        return first_name;
    }
    public String getLast_name(){
        return last_name;
    }
    public String getEmail(){
        return email;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword_hash(){
        return password_hash;
    }

}
