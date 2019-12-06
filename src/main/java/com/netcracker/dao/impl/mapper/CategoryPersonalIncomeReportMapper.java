package com.netcracker.dao.impl.mapper;

import com.netcracker.models.CategoryIncomeReport;
import com.netcracker.models.enums.CategoryIncome;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryPersonalIncomeReportMapper implements RowMapper<CategoryIncomeReport> {
    @Override
    public CategoryIncomeReport mapRow(ResultSet resultSet, int i) throws SQLException {
        CategoryIncomeReport categoryIncomeReport =
                new CategoryIncomeReport.Builder()
                    .idIncomeReport(resultSet.getBigDecimal("category_income_report").toBigInteger())
                    .amount(resultSet.getBigDecimal("amount"))
                    .income(CategoryIncome.getNameByKey(resultSet.getBigDecimal("category").toBigInteger()))
                    .personalReference(resultSet.getBigDecimal("personal_debit").toBigInteger())
                    .build();
        return categoryIncomeReport;
    }
}
