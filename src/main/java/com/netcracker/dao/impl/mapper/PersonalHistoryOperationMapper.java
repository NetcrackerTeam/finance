package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AbstractAccountOperation;
import com.netcracker.models.HistoryOperation;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class PersonalHistoryOperationMapper implements RowMapper<HistoryOperation> {

    @Override
    public HistoryOperation mapRow(ResultSet resultSet, int i) throws SQLException {
        AbstractAccountOperation accountOperation =
                new HistoryOperation.Builder()
                        .accountAmount(resultSet.getDouble("AMOUNT"))
                        .accountDate(new Timestamp(resultSet.getDate("DATE_IN").getTime()).toLocalDateTime())
                        .categoryExpense(CategoryExpense.getNameByKey(getKeyCategory(resultSet.getBigDecimal("CATEGORY_EXPENSE"))))
                        .categoryIncome(CategoryIncome.getNameByKey(getKeyCategory(resultSet.getBigDecimal("CATEGORY_INCOME"))))
                        .build();
        return (HistoryOperation) accountOperation;
    }

    private BigInteger getKeyCategory(BigDecimal var){
        if (var == null){
            return null;
        } else {
            return var.toBigInteger();
        }
    }
}
