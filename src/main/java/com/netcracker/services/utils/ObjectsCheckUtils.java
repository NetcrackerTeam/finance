package com.netcracker.services.utils;

import com.netcracker.exception.NullObjectException;

public final class ObjectsCheckUtils {

    public ObjectsCheckUtils() {
        throw new UnsupportedOperationException();
    }

    public static void isNotNull(final Object... objects) {
        if (objects == null) {
            throw new NullObjectException();
        }

        for (final Object obj : objects) {
            if (obj == null)
                throw new NullObjectException();
        }
    }
}
