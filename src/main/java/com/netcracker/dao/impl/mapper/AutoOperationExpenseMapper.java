package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.enums.CategoryExpense;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoOperationExpenseMapper implements RowMapper<AutoOperationExpense> {

    @Override
    public AutoOperationExpense mapRow(ResultSet resultSet, int i) throws SQLException {
        return new AutoOperationExpense.Builder()
                .accountId(resultSet.getBigDecimal("ao_object_id").toBigInteger())
                .accountUserId(resultSet.getBigDecimal("user_id").toBigInteger())
                .categoryExpense(CategoryExpense.getNameByKey(resultSet.getBigDecimal("category_id").toBigInteger()))
                .accountAmount(resultSet.getLong("amount"))
                .accountDate(resultSet.getDate("date_of_creation").toLocalDate())
                .dayOfMonth(resultSet.getInt("day_of_month"))
                .build();
    }
}