package com.netcracker.dao.impl.mapper;

import com.netcracker.models.CreditOperation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditOperationMapper implements RowMapper<CreditOperation> {
    @Override
    public CreditOperation mapRow(ResultSet resultSet, int i) throws SQLException {
        CreditOperation creditOperation = new CreditOperation( resultSet.getLong("amount"),
                resultSet.getDate("date_value"));
        creditOperation.setCreditOperationId(resultSet.getBigDecimal("operation_id").toBigInteger());
        return creditOperation;
    }
}
