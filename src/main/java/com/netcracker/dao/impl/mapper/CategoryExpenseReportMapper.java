package com.netcracker.dao.impl.mapper;

import com.netcracker.models.CategoryExpenseReport;
import com.netcracker.models.enums.CategoryExpense;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryExpenseReportMapper implements RowMapper<CategoryExpenseReport> {

    @Override
    public CategoryExpenseReport mapRow(ResultSet resultSet, int i) throws SQLException {

        CategoryExpenseReport categoryExpenseReport =
                new CategoryExpenseReport.Builder()
                        .idExpenseReport(resultSet.getBigDecimal("category_expense_report").toBigInteger())
                        .amount(resultSet.getBigDecimal("amount"))
                        .expense(CategoryExpense.getNameByKey(resultSet.getBigDecimal("category").toBigInteger()))
                        .build();
        return categoryExpenseReport;
    }
}
