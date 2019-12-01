package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountExpenseMapper implements RowMapper<AccountExpense> {

    @Override
    public AccountExpense mapRow(ResultSet resultSet, int i) throws SQLException {
        AbstractAccountOperation accountExpense =
                new AccountExpense.Builder()
                        .accountId(resultSet.getBigDecimal("account_expense_id").toBigInteger())
                        .accountAmount(resultSet.getLong("expense_amount"))
                        .accountDate(resultSet.getDate("date_expense"))
                        .categoryExpense(CategoryExpense.getNameByKey(resultSet.getBigDecimal("category_expense").toBigInteger()))
                        .build();
        return (AccountExpense) accountExpense;
    }
}
