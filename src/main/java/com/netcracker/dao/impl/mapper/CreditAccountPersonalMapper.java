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
                .creditId(resultSet.getBigDecimal("credit_id").toBigInteger())
                .name(resultSet.getString("name"))
                .amount(Long.valueOf(resultSet.getString("amount")))
                .paidAmount(Long.valueOf(resultSet.getString("paid")))
                .monthDay(Integer.valueOf(resultSet.getString("month_day")))
                .isPaid(Boolean.parseBoolean(resultSet.getString("is_paid")))
                .build();

        return (PersonalCreditAccount) personalCreditAccount;
    }
}
