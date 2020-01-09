package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.HistoryOperation;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryOperationMapper implements RowMapper<HistoryOperation> {

    @Override
    public HistoryOperation mapRow(ResultSet resultSet, int i) throws SQLException {
        AbstractAccountOperation accountOperation =
                new HistoryOperation.Builder()
                        .accountId(resultSet.getBigDecimal("TRANSACTION_ID").toBigInteger())
                        .accountAmount(resultSet.getDouble("AMOUNT"))
                        .accountDate(resultSet.getDate("DATE_IN").toLocalDate())
                        .categoryExpense(CategoryExpense.getNameByKey(resultSet.getBigDecimal("CATEGORY_EXPENSE").toBigInteger()))
                        .categoryIncome(CategoryIncome.getNameByKey(resultSet.getBigDecimal("CATEGORY_INCOME").toBigInteger()))
                        .build();
        return (HistoryOperation) accountOperation;
    }
}
