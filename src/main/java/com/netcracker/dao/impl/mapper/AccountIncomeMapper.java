package com.netcracker.dao.impl.mapper;

import com.netcracker.dao.OperationDao;
import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.Debt;
import com.netcracker.models.enums.CategoryIncome;
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
                .accountAmount(resultSet.getBigDecimal("income_amount").toBigInteger())
                .accountDate(resultSet.getDate("date_of").toLocalDate())
                .categoryIncome(CategoryIncome.getNameByKey(resultSet.getBigDecimal("category").toBigInteger()))
                .accountUserId(resultSet.getLong("user_id"))
                .build();
        return (AccountIncome) accountIncome;
    }
}
