package com.netcracker.dao.utils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.util.Objects;

public final class ObjectsCreator {

    public static BigInteger createObject(int firstParameter, String secondParameter, JdbcTemplate jdbcTemplate,
                                          String queryForCreateObject) {
        final String[] generatedColumns = { "OBJECT_ID" };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(queryForCreateObject, generatedColumns);
            ps.setInt(1, firstParameter);
            ps.setString(2, secondParameter);
            return ps;
        }, keyHolder);

        return new BigInteger(Objects.requireNonNull(keyHolder.getKey(), "OBJECT_ID must not be null").toString());
    }
}
