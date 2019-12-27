package com.netcracker.services.utils;

import com.netcracker.exception.NullObjectException;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;

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

    public static Collection<Object> collectionIsEmpty(Collection<Object> collection) {
        if (CollectionUtils.isEmpty(collection)) return Collections.emptyList();
        else return collection;
    }
}
