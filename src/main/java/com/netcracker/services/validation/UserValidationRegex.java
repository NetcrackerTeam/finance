package com.netcracker.services.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidationRegex {
    private Pattern pattern;

    private Matcher matcher;

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String NAME_PATTERN = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){5,24}$";

    public boolean validateEmail(String email) {
        if (email != null) {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
            return matcher.matches();
        } else
            return false;
    }

    public boolean validatePassword(String password) {
        if (password != null) {
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);
            return matcher.matches();
        } else
            return false;
    }

    public boolean validateName(String name) {
        if (name != null) {
            pattern = Pattern.compile(NAME_PATTERN);
            matcher = pattern.matcher(name);
            return matcher.matches();
        } else
            return false;
    }
}
