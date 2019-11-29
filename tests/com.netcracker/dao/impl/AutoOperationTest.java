package com.netcracker.dao.impl;
import com.netcracker.configs.WebConfig;
import com.netcracker.dao.AutoOperationDao;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

import java.math.BigInteger;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class AutoOperationTest {
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AutoOperationDao autoOperationDao;

    @Before
    public void setUp() {
        Locale.setDefault(Locale.ENGLISH);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void getFamilyIncomeAutoOperation() {
        AutoOperationIncome autoOperationIncome = autoOperationDao.getFamilyIncomeAutoOperation(9004);
        assertEquals(BigInteger.valueOf(9004), autoOperationIncome.getId());
        assertEquals("9004 3 4 3500 34 2019-11-28", autoOperationIncome.getId() + " " + autoOperationIncome.getUserId() + " " +
                autoOperationIncome.getDayOfMonth() + " " + autoOperationIncome.getAmount() + " " +
                autoOperationIncome.getCategoryIncome().getId() + " " + autoOperationIncome.getDate());
    }

    @Test
    public void getFamilyExpenseAutoOperation() {
        AutoOperationExpense autoOperationExpense = autoOperationDao.getFamilyExpenseAutoOperation(9003);
        assertEquals(BigInteger.valueOf(9003), autoOperationExpense.getId());
        assertEquals("9003 3 1 3 3000 16 2019-11-29", autoOperationExpense.getId() + " " +
                autoOperationExpense.getUserId() + " " + autoOperationExpense.getDayOfMonth() + " " +
                autoOperationExpense.getAmount() + " " + autoOperationExpense.getCategoryExpense().getId() + " " +
                autoOperationExpense.getDate());
    }

    @Test
    public void getPersonalIncomeAutoOperation() {

    }
}
