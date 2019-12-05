package com.netcracker.services.validation;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation extends AbstractValidation {

    public Map<String, String> validateInputId(String id) {
        validateInputId(id);
        return getErrorMapMessage();
    }


    public Map<String,String> validationEmail(String email){
        if (!checkEmail(email)){
            setErrorMapMessage("WRONG_EMAIL_ERROR",
                    ErrorMassage.WRONG_EMAIL_FORMAT);
        }
        return getErrorMapMessage();
    }

    public Map<String, String > validationPassword (String password ){
        if(password.equals(null)){
            setErrorMapMessage("PASSWORD NULL ", ErrorMassage.WRONG_PASSWORD);
        }
        return getErrorMapMessage();
    }

    private boolean checkEmail(String name) {
        Pattern p = Pattern.compile(RegexPatterns.EMAIL_REGEX_PATTERN);
        Matcher m = p.matcher(name);
        return m.matches();
    }

}