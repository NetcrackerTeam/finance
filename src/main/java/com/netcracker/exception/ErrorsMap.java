package com.netcracker.exception;

import com.netcracker.controllers.MessageController;
import com.netcracker.services.utils.ExceptionMessages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ErrorsMap {
    private static final Map<String, String> errorsMap;

    static {
        Map<String, String>  tempMap = new HashMap<>();
        tempMap.put(ExceptionMessages.NOT_ENOUGH_MONEY_ERROR, MessageController.NOT_ENOUGH_MONEY_MESSAGE);
        tempMap.put(ExceptionMessages.PASS_LOWER, MessageController.PASS_LOWERCASE);
        tempMap.put(ExceptionMessages.PASS_UPPER, MessageController.PASS_UPPERCASE);
        tempMap.put(ExceptionMessages.PASS_NUM, MessageController.PASS_NUM);
        tempMap.put(ExceptionMessages.PASS_SHORT, MessageController.PASS_SHORT);
        tempMap.put(ExceptionMessages.INVALID_EMAIL, MessageController.EMAIL_INVALID);
        tempMap.put(ExceptionMessages.INVALID_PASSWORD, MessageController.PASS_INVALID);
        tempMap.put(ExceptionMessages.EMPTY_FIELD, MessageController.EMPTY_FIELD);
        tempMap.put(ExceptionMessages.USER_ALREADY_EXIST, MessageController.USER_ALREADY_EXISTS);
        tempMap.put(ExceptionMessages.LATIN_CHAR, MessageController.LATIN_CHAR);
        tempMap.put(ExceptionMessages.LATIN_LETTERS, MessageController.LATIN_LETTERS);
        tempMap.put(ExceptionMessages.NAME_SHORT, MessageController.NAME_SHORT);
        errorsMap = Collections.unmodifiableMap(tempMap);
    }

    public static Map<String, String> getErrorsMap() {
        return errorsMap;
    }

}
