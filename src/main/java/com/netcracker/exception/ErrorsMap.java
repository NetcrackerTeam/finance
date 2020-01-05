package com.netcracker.exception;

import com.netcracker.services.utils.ExceptionMessages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ErrorsMap {
    public static final String CODE_NOT_MONEY = "NOT_MONEY";
    public static final  String NOT_MONEY = "Not enough money on debit account";

    private static final Map<String, String> errorsMap;

    static {
        Map<String, String>  tempMap = new HashMap<>();
        tempMap.put(CODE_NOT_MONEY, ExceptionMessages.NOT_ENOUGH_MONEY_ERROR);
        errorsMap = Collections.unmodifiableMap(tempMap);
    }

    public static Map<String, String> getErrorsMap() {
        return errorsMap;
    }

}
