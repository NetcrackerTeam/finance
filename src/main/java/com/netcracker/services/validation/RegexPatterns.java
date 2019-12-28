package com.netcracker.services.validation;

public interface RegexPatterns {
    String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    String NAME_PATTERN = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){5,24}$";
}
