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
                .totalIncome(resultSet.getLong("income"))
                .totalExpense(resultSet.getLong("expense"))
                .balance(resultSet.getLong("balance"))
                .date(resultSet.getDate("date_of").toLocalDate())
                .build();
        return monthReport;
    }
}
