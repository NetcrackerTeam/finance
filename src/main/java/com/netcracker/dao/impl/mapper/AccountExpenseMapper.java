package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountExpenseMapper implements RowMapper<AccountExpense> {

    @Override
    public AccountExpense mapRow(ResultSet resultSet, int i) throws SQLException {
        AbstractAccountOperation accountExpense =
                new AccountExpense.Builder()
                        .accountId(resultSet.getBigDecimal("account_income_id").toBigInteger())
                        .accountAmount(resultSet.getLong("income_amount"))
                        .accountDate(resultSet.getDate("date").toLocalDate())
                        .accountUserId(resultSet.getBigDecimal("user_id").toBigInteger())
                        .build();
        return (AccountExpense) accountExpense;
    }
}
