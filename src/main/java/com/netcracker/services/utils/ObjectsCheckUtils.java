package com.netcracker.services.utils;

public final class ObjectsCheckUtils {

    private final static String NULL_MESSAGE = "Null object was found";

    public ObjectsCheckUtils() {
        throw new UnsupportedOperationException();
    }

    public static void isNotNull(final Object... objects) {
        if (objects == null) {
            throw new RuntimeException(NULL_MESSAGE);
        }

        for (final Object obj : objects) {
            if (obj == null) {
                throw new RuntimeException("Null object was found");
            }
            if (obj.equals(0)) throw new RuntimeException("Null object was found");
        }
    }
}
