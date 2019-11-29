package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.enums.CategoryIncome;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoOperationIncomeMapper implements RowMapper<AutoOperationIncome> {

    @Override
    public AutoOperationIncome mapRow(ResultSet resultSet, int i) throws SQLException {
        return new AutoOperationIncome.Builder()
                .accountId(resultSet.getBigDecimal("ao_object_id").toBigInteger())
                .accountUserId(resultSet.getBigDecimal("user_id").toBigInteger())
                .categoryIncome(CategoryIncome.getNameByKey(resultSet.getBigDecimal("category_id").toBigInteger()))
                .accountAmount(resultSet.getLong("amount"))
                .accountDate(resultSet.getDate("date_of_creation"))
                .dayOfMonth(resultSet.getInt("day_of_month"))
                .build();
    }
}
