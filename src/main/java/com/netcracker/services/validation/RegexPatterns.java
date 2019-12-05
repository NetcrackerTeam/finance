package com.netcracker.services.validation;

public interface RegexPatterns {
    String ID_REGEX_PATTERN = "^\\d*$";
    String EMAIL_REGEX_PATTERN = "^(\\S+)@([a-z0-9-]+)(\\.)([a-z]{2,4})(\\.?)([a-z]{0,4})+$";

}
