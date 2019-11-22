package com.netcracker.dao.impl;

import com.netcracker.dao.AutoOperationDao;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.CategoryExpense;
import com.netcracker.models.CategoryIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AutoOperationDaoImpl implements AutoOperationDao {
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public AutoOperationDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public AutoOperationIncome getAutoOperationIncomeById(BigInteger autoOperationId) {
        return jdbcTemplate.queryForObject(GET_INCOME_AO, new Object[] {autoOperationId}, new AutoOperationIncomeMapper());
    }

    @Override
    public AutoOperationExpense getAutoOperationExpenseById(BigInteger autoOperationId) {
        return null;
    }

    @Override
    public void createFamilyIncomeAutoOperation(CategoryIncome income) {

    }

    @Override
    public void createPersonalIncomeAutoOperation(CategoryIncome income) {

    }

    @Override
    public void deleteFamilyIncomeAutoOperation(BigInteger autoOperationId) {

    }

    @Override
    public void deletePersonalIncomeAutoOperation(CategoryIncome income) {

    }

    @Override
    public void createFamilyExpenseAutoOperation(CategoryExpense expense) {

    }

    @Override
    public void createPersonalExpenseAutoOperation(CategoryExpense expense) {

    }

    @Override
    public void deleteFamilyIncExpenseOperation(BigInteger autoOperationId) {

    }

    @Override
    public void deletePersonalExpenseAutoOperation(BigInteger autoOperationId) {

    }

    class AutoOperationIncomeMapper implements RowMapper<AutoOperationIncome> {

        public AutoOperationIncome mapRow(ResultSet resultSet, int i) throws SQLException {
            return null;
            /*AutoOperationIncome autoOperationIncome = new AutoOperationIncome()
                    .setCategoryIncome(CategoryIncome.valueOf(resultSet.getString("name")));*/
        }
    }
}
