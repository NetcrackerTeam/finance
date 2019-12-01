package com.netcracker.dao.impl.mapper;

import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDaoMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        PersonalDebitAccountMapper personalDebitAccountMapper = new PersonalDebitAccountMapper();
        PersonalDebitAccount pDebit = personalDebitAccountMapper.mapRow(resultSet, i);

        FamilyAccountDebitMapper familyDebitAccountM = new FamilyAccountDebitMapper();
        FamilyDebitAccount familyDeBAcc = familyDebitAccountM.mapRow(resultSet, i);


        return new User.Builder()
                .user_id(resultSet.getBigDecimal("USER_ID").toBigInteger())
                .user_name(resultSet.getString("NAME"))
                .user_eMail(resultSet.getString("EMAIL"))
                .user_password(resultSet.getString("PASSWORD"))
                .userActive(UserStatusActive.valueOf(resultSet.getString("IS_ACTIVE")))
                .personalDebit(pDebit)
                .familyDebit(familyDeBAcc).build();
    }
}
