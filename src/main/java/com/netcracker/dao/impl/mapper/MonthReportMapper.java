package com.netcracker.dao.impl.mapper;

import com.netcracker.models.MonthReport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MonthReportMapper implements RowMapper<MonthReport> {
    @Override
    public MonthReport mapRow(ResultSet resultSet, int i) throws SQLException {
        MonthReport monthReport =
                new MonthReport.Builder()
                .id(resultSet.getBigDecimal("month_report_id").toBigInteger())
                .totalIncome(resultSet.getDouble("income"))
                .totalExpense(resultSet.getDouble("expense"))
                .balance(resultSet.getDouble("balance"))
                .dateFrom(resultSet.getDate("date_to").toLocalDate())
                .dateTo(resultSet.getDate("date_from").toLocalDate())
                .build();
        return monthReport;
    }
}
