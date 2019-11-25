package com.netcracker.dao.impl.mapper;

import com.netcracker.models.Debt;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditDebtMapper implements RowMapper<Debt> {

    @Override
    public Debt mapRow(ResultSet resultSet, int i) throws SQLException {
        Debt debt = new Debt.Builder()
                .debtId(resultSet.getBigDecimal("debt_id").toBigInteger())
                .dateFrom(resultSet.getDate("debt_from"))
                .dateTo(resultSet.getDate("debt_to"))
                .amountDebt(Long.valueOf(resultSet.getString("debt_amount")))
                .build();
        return debt;
    }
}
