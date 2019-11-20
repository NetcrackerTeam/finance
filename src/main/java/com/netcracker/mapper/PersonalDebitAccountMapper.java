package com.netcracker.mapper;

import com.netcracker.models.PersonalDebitAccount;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalDebitAccountMapper implements RowMapper<PersonalDebitAccount> {
    @Override
    public PersonalDebitAccount mapRow(ResultSet rs, int i) throws SQLException {
        PersonalDebitAccount pda = new PersonalDebitAccount();
        pda.setId(new BigInteger(Integer.valueOf(rs.getInt("OBJECT_ID")).toString()));
        pda.setAmount(rs.getLong("Amount"));
        pda.setObjectName(rs.getString("Owner"));
        return pda;
    }
}
