package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.enums.CategoryExpense;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;

public class AccountExpenseMapper implements RowMapper<AccountExpense> {

    @Override
    public AccountExpense mapRow(ResultSet resultSet, int i) throws SQLException {
        AbstractAccountOperation accountExpense =
                new AccountExpense.Builder()
                        .accountId(resultSet.getBigDecimal("account_expense_id").toBigInteger())
                        .accountAmount(resultSet.getDouble("expense_amount"))
                        .accountDate(new Timestamp(resultSet.getDate("date_expense").getTime()).toLocalDateTime())
                        .categoryExpense(CategoryExpense.getNameByKey(resultSet.getBigDecimal("category_expense").toBigInteger()))
                        .build();
        return (AccountExpense) accountExpense;
    }
}
