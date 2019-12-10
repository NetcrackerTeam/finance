package com.netcracker.dao.impl.mapper;

import com.netcracker.models.Debt;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CreditDebtMapper implements RowMapper<Debt> {

    @Override
    public Debt mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Debt.Builder()
                .debtId(resultSet.getBigDecimal("debt_id").toBigInteger())
                .dateFrom(checkNullDate(resultSet.getDate("debt_from")))
                .dateTo(checkNullDate(resultSet.getDate("debt_to")))
                .amountDebt(Long.valueOf(resultSet.getString("debt_amount")))
                .build();
    }

    private LocalDate checkNullDate(Date date) {
        return date == null ? null : date.toLocalDate();
    }
}
