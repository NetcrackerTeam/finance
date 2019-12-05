package com.netcracker.services.validation;
import com.netcracker.services.validation.errorMessage.ErrorMessages;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation extends AbstractValidation {

    public Map<String, String> validateInputId(String id) {
        validateId(id);
        return getErrorMapMessage();
    }


    public Map<String,String> validationEmail(String email){
        if (!checkEmail(email)){
            setErrorToMapMessage("WRONG_EMAIL_ERROR",
                    ErrorMessages.WRONG_EMAIL_FORMAT);
        }
        return getErrorMapMessage();
    }

    public Map<String, String >  validatePassword(String password) {
        if (!checkPassword(password)) {
            setErrorToMapMessage("PASSWORD ERROR FORMAT",
                    ErrorMessages.PASSWORD_ERROR);
        }
        return getErrorMapMessage();
    }

    private boolean checkEmail(String name) {
        Pattern p = Pattern.compile(RegexPatterns.EMAIL_PATTERN);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    private boolean checkPassword(String name) {
        Pattern p = Pattern.compile(RegexPatterns.PASSWORD_PATTERN);
        Matcher m = p.matcher(name);
        return m.matches();
    }

}