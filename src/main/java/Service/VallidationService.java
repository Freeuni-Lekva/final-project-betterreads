package Service;

public class VallidationService implements VallidationServiceInterface{
    @Override
    public boolean isFirstNameCorrect(String firstname) {
        return !firstname.isEmpty();
    }

    @Override
    public boolean isLastNameCorrect(String lastname) {
        return !lastname.isEmpty();
    }

    @Override
    public boolean isMailCorrect(String mail) {
        return mail.endsWith("@freeuni.edu.ge") || mail.endsWith("@gmail.com");
    }

    @Override
    public boolean isUsernameEmpty(String userName) {
        return  userName.isEmpty();
    }

    @Override
    public boolean isPasswordCorrect(String password) {
        return password.length() >= 7;
    }

    @Override
    public String getErrorMessage(String firstname, String lastname, String mail,
                                  String password, String username) {
        if(!isFirstNameCorrect(firstname)){
            return "First name can't be empty";
        } else if(!isLastNameCorrect(lastname)){
            return "Last name can't be empty";
        } else if(isUsernameEmpty(username)){
            return "Username can't be empty";
        } else if(!isMailCorrect(mail)){
            return "Mail is not correct";
        } else if(!isPasswordCorrect(password)){
            return "Password is too short";
        }
        return "";
    }
}
