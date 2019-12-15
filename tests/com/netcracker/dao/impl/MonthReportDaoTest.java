package com.netcracker.dao.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.MonthReportDao;

import com.netcracker.models.AccountIncome;
import com.netcracker.models.MonthReport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MonthReportDaoTest {

    private MonthReport expMonthReportPersonal;
    private MonthReport expMonthReportFamily;

    @Autowired
    private MonthReportDao monthReportDao;

    @Before
    public void initializeObjects() {
        expMonthReportFamily = new MonthReport.Builder()
                .totalIncome(5000L)
                .totalExpense(4500L)
                .balance(9500L)
                .dateTo(LocalDate.of(2019,12, 1))
                .dateFrom(LocalDate.of(2019,12,30))
                .build();

        expMonthReportPersonal = new MonthReport.Builder()
                .totalIncome(4000L)
                .totalExpense(3500L)
                .balance(5500L)
                .dateTo(LocalDate.of(2019,11,1))
                .dateFrom(LocalDate.of(2019,11,30))
                .build();
    }

    /*TODO
    *  remake tests */

    @Rollback
    @Test
    public void createPersonalMonthReport() {
        monthReportDao.createPersonalMonthReport(expMonthReportPersonal, new BigInteger("2"));

       // List<MonthReport> list = monthReportDao.getMonthReportByPersonalAccountId(new BigInteger("2"), );
        //int expected = 2;
       // Assert.assertEquals(expected, list.size());
    }

    @Rollback
    @Test
    public void createFamilyMonthReport() {
        monthReportDao.createFamilyMonthReport(expMonthReportFamily, new BigInteger("3"));
       // List<MonthReport> list = monthReportDao.getMonthReportByFamilyAccountId(new BigInteger("3"));
        //int expected = 2;
       // Assert.assertEquals(expected, list.size());
    }


    @Test
    public void getMonthReportsByFamilyAccountId() {
       // List<MonthReport> list = monthReportDao.getMonthReportByFamilyAccountId(new BigInteger("3"));
       // Assert.assertEquals(1, list.size());
    }

    @Test
    public void getMonthReportsByPersonalAccountId() {
       // List<MonthReport> list = monthReportDao.getMonthReportByPersonalAccountId(new BigInteger("2"));
       // Assert.assertEquals(1, list.size());
    }
}
