package com.netcracker.dao.impl.mapper;


import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.AccountIncome;

import com.netcracker.models.enums.CategoryIncome;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;

public class AccountIncomeMapper implements RowMapper<AccountIncome> {
    @Override
    public AccountIncome mapRow(ResultSet resultSet, int i) throws SQLException {
        AbstractAccountOperation accountIncome =
                new AccountIncome.Builder()
                        .accountId(resultSet.getBigDecimal("account_income_id").toBigInteger())
                        .accountAmount(resultSet.getDouble("income_amount"))
                        .accountDate(new Timestamp(resultSet.getDate("date_income").getTime()).toLocalDateTime())
                        .categoryIncome(CategoryIncome.getNameByKey(resultSet.getBigDecimal("category_income").toBigInteger()))
                        .build();
        return (AccountIncome) accountIncome;
    }
}
