package com.netcracker.dao.impl.mapper;

import com.netcracker.models.CategoryIncomeReport;
import com.netcracker.models.enums.CategoryIncome;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryIncomeReportMapper implements RowMapper<CategoryIncomeReport> {
    @Override
    public CategoryIncomeReport mapRow(ResultSet resultSet, int i) throws SQLException {
        CategoryIncomeReport categoryIncomeReport =
                new CategoryIncomeReport.Builder()
                    .idIncomeReport(resultSet.getBigDecimal("category_expense_report").toBigInteger())
                    .amount(resultSet.getBigDecimal("amount"))
                    .income(CategoryIncome.getNameByKey(resultSet.getBigDecimal("category").toBigInteger()))
                    .build();
        return categoryIncomeReport;
    }
}
