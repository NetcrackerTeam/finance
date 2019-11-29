package com.netcracker.dao.impl.mapper;

import com.netcracker.dao.OperationDao;
import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.Debt;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountIncomeMapper implements RowMapper<AccountIncome> {
    @Override
    public AccountIncome mapRow(ResultSet resultSet, int i) throws SQLException {
        AbstractAccountOperation accountIncome =
                new AccountIncome.Builder()
                .accountId(resultSet.getBigDecimal("account_income_id").toBigInteger())
                .accountAmount(resultSet.getLong("income_amount"))
                .accountDate(resultSet.getDate("date_income"))
                .accountUserId(resultSet.getBigDecimal("user_id").toBigInteger())
                .build();
        return (AccountIncome) accountIncome;
    }
}
