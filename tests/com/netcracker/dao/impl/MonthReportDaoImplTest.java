package com.netcracker.dao.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.MonthReportDao;

import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.MonthReport;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
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

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.*;

@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MonthReportDaoImplTest {

    private MonthReport expMonthReportPersonal;
    private MonthReport expMonthReportFamily;

    @Autowired
    private MonthReportDao operationDao;

    @Before
    public void initializeObjects() {
        expMonthReportFamily = new MonthReport.Builder()
                .totalIncome(5000L)
                .totalExpense(4500L)
                .balance(9500L)
                .date_to(LocalDate.of(2019,12, 1))
                .date_from(LocalDate.of(2019,12,30))
                .build();

        expMonthReportPersonal = new MonthReport.Builder()
                .totalIncome(4000L)
                .totalExpense(3500L)
                .balance(5500L)
                .date_to(LocalDate.of(2019,11,1))
                .date_from(LocalDate.of(2019,11,30))
                .build();
    }

    @Rollback
    @Test
    public void createPersonalMonthReport() {
    }

    @Rollback
    @Test
    public void createFamilyMonthReport() {
    }

    @Rollback
    @Test
    public void deletePersonalMonthReport() {
    }

    @Rollback
    @Test
    public void deleteFamilyMonthReport() {
    }


    @Test
    public void getMonthReportByFamilyAccountId() {
        MonthReport actual =  operationDao.getMonthReportByFamilyAccountId(BigInteger.valueOf(3));
        Assert.assertEquals(expMonthReportFamily.getBalance(), actual.getBalance());
        Assert.assertEquals(expMonthReportFamily.getDate_from(), actual.getDate_from());
        Assert.assertEquals(expMonthReportFamily.getDate_to(), actual.getDate_to());
        Assert.assertEquals(expMonthReportFamily.getTotalExpense(), actual.getTotalExpense());
        Assert.assertEquals(expMonthReportFamily.getTotalIncome(), actual.getTotalIncome());
    }

    @Test
    public void getMonthReportByPersonalAccountId() {
        MonthReport actual =  operationDao.getMonthReportByPersonalAccountId(BigInteger.valueOf(3));
        Assert.assertEquals(expMonthReportPersonal.getBalance(), actual.getBalance());
        Assert.assertEquals(expMonthReportPersonal.getDate_from(), actual.getDate_from());
        Assert.assertEquals(expMonthReportPersonal.getDate_to(), actual.getDate_to());
        Assert.assertEquals(expMonthReportPersonal.getTotalExpense(), actual.getTotalExpense());
        Assert.assertEquals(expMonthReportPersonal.getTotalIncome(), actual.getTotalIncome());
    }
}
