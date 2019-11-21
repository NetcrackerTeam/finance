package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AbstractCreditAccount;
import com.netcracker.models.PersonalCreditAccount;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditAccountPersonalMapper implements RowMapper<PersonalCreditAccount> {

    @Override
    public PersonalCreditAccount mapRow(ResultSet resultSet, int i) throws SQLException {
        AbstractCreditAccount personalCreditAccount =
                new PersonalCreditAccount.Builder()
                .creditId(resultSet.getBigDecimal("OBJECT_ID").toBigInteger())
                .name(resultSet.getString("VALUE"))
                .amount(Long.valueOf(resultSet.getString("VALUE")))
                .paidAmount(Long.valueOf(resultSet.getString("VALUE")))
                .monthDay(Integer.valueOf(resultSet.getString("VALUE")))
                .isPaid(Boolean.parseBoolean(resultSet.getString("VALUE")))
                .build();

        return (PersonalCreditAccount) personalCreditAccount;
    }
}
