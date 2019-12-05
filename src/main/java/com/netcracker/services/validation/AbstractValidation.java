package com.netcracker.services.validation;

import com.netcracker.services.validation.errorMessage.ErrorMessages;
import com.netcracker.models.enums.UserStatusActive;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract  class AbstractValidation {

    private Map<String, String> errorMap;

    AbstractValidation() {
        errorMap = new HashMap<>();
    }

    void validateId(String id) {
        if (StringUtils.isEmpty(id)) {
            errorMap.put("EMPTY_ID_ERROR", ErrorMessages.ERROR_ID);
            return;
        }

        if (!checkId(id)) {
            errorMap
                    .put("WRONG_ID_FORMAT_ERROR", ErrorMessages.WRONG_ID_FORMAT);
            return;
        }

        try {
            BigInteger bigIntegerId = new BigInteger(id);
        } catch (NumberFormatException ex) {
            errorMap
                    .put("WRONG_ID_FORMAT_ERROR", ErrorMessages.WRONG_ID_FORMAT);
        }
    }

    void validateStatus(String status) {
        if (StringUtils.isEmpty(status)) {
            errorMap.put("EMPTY_STATUS_ERROR", ErrorMessages.EMPTY_STATUS_ERROR);
            return;
        }

        if (!statusEnumCheck(status)) {
            errorMap
                    .put("INCORRECT_STATUS_ERROR", ErrorMessages.INCORRECT_STATUS_ERROR);
        }
    }

    void validateName(String name) {
        if (!checkName(name)) {
            errorMap.put("USER_FIRST_OR_LAST_NAME_ERROR",
                    ErrorMessages.USER_FIRST_OR_LAST_NAME_ERROR);
        }
    }


    Map<String, String> getErrorMapMessage() {
        return this.errorMap;
    }

    void setErrorToMapMessage(String errorName, String errorDescription) {
        this.errorMap.put(errorName, errorDescription);
    }

    private boolean statusEnumCheck(String status) {
        for (UserStatusActive enumStatus : UserStatusActive.values()) {
            if (enumStatus.name().equals(status)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkStartDate(String startDate) {
        if (checkDate(startDate)) {
            errorMap.put("WRONG_START_DATE_FORMAT_ERROR",
                    ErrorMessages.WRONG_START_DATE_FORMAT_ERROR);
            return false;
        }

        return true;
    }

    private boolean checkEndDate(String endDate) {
        if (checkDate(endDate)) {
            errorMap.put("WRONG_END_DATE_FORMAT_ERROR",
                    ErrorMessages.WRONG_END_DATE_FORMAT_ERROR);
            return false;
        }

        return true;
    }

    private boolean checkId(String id) {
        Pattern p = Pattern.compile(RegexPatterns.ID_PATTERN);
        Matcher m = p.matcher(id);
        return m.matches();
    }

    private boolean checkDate(String dateString) {
        Pattern p = Pattern.compile(RegexPatterns.DATE_PATTERN);
        Matcher m = p.matcher(dateString);
        return m.matches();
    }

    private boolean checkName(String name) {
        Pattern p = Pattern.compile(RegexPatterns.NAME_PATTERN);
        Matcher m = p.matcher(name);
        return m.matches();
    }
}
