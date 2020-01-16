package com.netcracker.dao.impl.mapper;

import com.netcracker.models.MonthReport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MonthReportMapper implements RowMapper<MonthReport> {
    @Override
    public MonthReport mapRow(ResultSet resultSet, int i) throws SQLException {
        MonthReport monthReport =
                new MonthReport.Builder()
                        .id(resultSet.getBigDecimal("month_report_id").toBigInteger())
                        .totalIncome(resultSet.getDouble("income"))
                        .totalExpense(resultSet.getDouble("expense"))
                        .balance(resultSet.getDouble("balance"))
                        .dateFrom(new Timestamp(resultSet.getDate("date_to").getTime()).toLocalDateTime())
                        .dateTo(new Timestamp(resultSet.getDate("date_from").getTime()).toLocalDateTime())
                        .build();
        return monthReport;
    }
}
