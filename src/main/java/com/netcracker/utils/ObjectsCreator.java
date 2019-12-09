package com.netcracker.utils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.math.BigInteger;
import java.sql.PreparedStatement;

public class ObjectsCreator {

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

        return new BigInteger(keyHolder.getKey().toString());
    }
}
