package com.netcracker.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorsMap {
    private static Map<String, String> errorsMap = new HashMap<>();

    public static void setErrorToMap(String errorName, String errorDescription) {
        errorsMap.put(errorName, errorDescription);
    }

    public static Map<String, String> getErrorsMap() {
        return errorsMap;
    }

}
