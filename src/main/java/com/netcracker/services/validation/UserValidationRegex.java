package com.netcracker.services.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidationRegex {
    private Pattern pattern;

    private Matcher matcher;

    public boolean validateValueByUser(String val, String patterns ) {
        if (val != null) {
            pattern = Pattern.compile(patterns);
            matcher = pattern.matcher(val);
            return matcher.matches();
        } else
            return false;
    }
}
