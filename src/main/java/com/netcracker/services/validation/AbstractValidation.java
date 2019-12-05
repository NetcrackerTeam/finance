package com.netcracker.services.validation;

import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbstractValidation {
    private Map<String, String> errorMapMessage;

    AbstractValidation() {
        errorMapMessage = new HashMap<>();
    }

    Map<String, String> getErrorMapMessage() {
        return this.errorMapMessage;
    }
    void setErrorMapMessage(String errorName, String errorDescription) {
        this.errorMapMessage.put(errorName, errorDescription);
    }

    void validateId(String id) {
        if (StringUtils.isEmpty(id)) {
            errorMapMessage.put("EMPTY_ID_ERROR", ErrorMassage.ERROR_ID);
            return;
        }
        if (!checkId(id)) {
            errorMapMessage.put("WRONG_ID_FORMAT_ERROR", ErrorMassage.WRONG_ID_FORMAT);
            return;
        } try {
            BigInteger bigIntegerId = new BigInteger(id);
        } catch (NumberFormatException ex) {
            errorMapMessage.put("WRONG_ID_FORMAT_ERROR", ErrorMassage.WRONG_ID_FORMAT);
        }
    }




    private boolean checkId(String id) {
        Pattern p = Pattern.compile(RegexPatterns.ID_REGEX_PATTERN);
        Matcher m = p.matcher(id);
        return m.matches();
    }

}
