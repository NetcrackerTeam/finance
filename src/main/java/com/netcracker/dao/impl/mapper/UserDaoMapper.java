package com.netcracker.dao.impl.mapper;

import com.netcracker.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDaoMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return  new User.Builder()
                .user_id(new BigInteger(resultSet.getString("USER_ID")))
                .user_name(resultSet.getString("NAME"))
                .user_eMail(resultSet.getString("EMAIL"))
                .user_password(resultSet.getString("PASSWORD")).build();
             //   .personalDebit()
             //   .familyDebit(resultSet.getString("")).build();
    }
}
