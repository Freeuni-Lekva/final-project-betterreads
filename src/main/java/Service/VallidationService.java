package Service;

import Constants.SharedConstants;

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
        return mail.endsWith(SharedConstants.FREEUNI_MAIL) || mail.endsWith(SharedConstants.GMAIL);
    }

    @Override
    public boolean isUsernameEmpty(String userName) {
        return  userName.isEmpty();
    }

    @Override
    public boolean isPasswordCorrect(String password) {
        return password.length() >= SharedConstants.MIN_PASSWORD_LENGTH;
    }

    @Override
    public String getErrorMessage(String firstname, String lastname, String mail,
                                  String password, String username) {
        if(!isFirstNameCorrect(firstname)){
            return SharedConstants.FIRST_NAME_ERROR;
        } else if(!isLastNameCorrect(lastname)){
            return SharedConstants.LAST_NAME_ERROR;
        } else if(isUsernameEmpty(username)){
            return SharedConstants.USERNAME_ERROR;
        } else if(!isMailCorrect(mail)){
            return SharedConstants.EMAIL_ERROR;
        } else if(!isPasswordCorrect(password)){
            return SharedConstants.PASSWORD_ERROR;
        }
        return "";
    }
}
