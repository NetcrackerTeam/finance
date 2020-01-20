package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.enums.CategoryIncome;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;

public class AutoOperationIncomeMapper implements RowMapper<AutoOperationIncome> {

    @Override
    public AutoOperationIncome mapRow(ResultSet resultSet, int i) throws SQLException {
        AutoOperationIncome autoOperationIncome = new AutoOperationIncome.Builder()
                .accountId(resultSet.getBigDecimal("ao_object_id").toBigInteger())
                .accountDebitId(resultSet.getBigDecimal("debit_id").toBigInteger())
                .categoryIncome(CategoryIncome.getNameByKey(resultSet.getBigDecimal("category_id").toBigInteger()))
                .accountAmount(resultSet.getDouble("amount"))
                .accountDate(new Timestamp(resultSet.getDate("date_of_creation").getTime()).toLocalDateTime())
                .dayOfMonth(resultSet.getInt("day_of_month"))
                .build();

        if ("FAMILY_INCOME_AO".equals(resultSet.getString("ao_name")))
            autoOperationIncome.setUsername(resultSet.getString("username"));

        return autoOperationIncome;
    }
}
