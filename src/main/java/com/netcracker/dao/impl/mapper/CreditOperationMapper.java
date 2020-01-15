package com.netcracker.dao.impl.mapper;

import com.netcracker.models.CreditOperation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditOperationMapper implements RowMapper<CreditOperation> {
    @Override
    public CreditOperation mapRow(ResultSet resultSet, int i) throws SQLException {
        CreditOperation creditOperation = new CreditOperation(resultSet.getDouble("amount"),
                resultSet.getDate("date_value").toLocalDate());
        creditOperation.setCreditOperationId(resultSet.getBigDecimal("operation_id").toBigInteger());
        if ("CREDIT_OPERATION_FAMILY".equals(resultSet.getString("operation_name")))
            creditOperation.setUsername(resultSet.getString("username"));
        return creditOperation;
    }
}
