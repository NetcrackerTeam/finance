package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AbstractCategoryReport;
import com.netcracker.models.CategoryExpenseReport;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.ReportCategoryExpense;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryExpensePersonalReportMapper implements RowMapper<CategoryExpenseReport> {
    @Override
    public CategoryExpenseReport mapRow(ResultSet resultSet, int i) throws SQLException {

        AbstractCategoryReport categoryExpenseReport =
                new CategoryExpenseReport.Builder()
                        .amount(resultSet.getDouble("amount"))
                        .categoryExpense(ReportCategoryExpense.getNameByKey(resultSet.getBigDecimal("category").toBigInteger()))
                        .build();
        return (CategoryExpenseReport) categoryExpenseReport;
    }
}
