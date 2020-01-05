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
        errorsMap = Collections.unmodifiableMap(tempMap);
    }

    public static Map<String, String> getErrorsMap() {
        return errorsMap;
    }

}
