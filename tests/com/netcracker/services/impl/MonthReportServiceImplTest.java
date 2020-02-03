package com.netcracker.services.impl;


import com.netcracker.configs.WebConfig;
import com.netcracker.dao.*;
import com.netcracker.models.CategoryExpenseReport;
import com.netcracker.models.CategoryIncomeReport;
import com.netcracker.models.MonthReport;
import com.netcracker.models.User;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.models.enums.ReportCategoryExpense;
import com.netcracker.models.enums.ReportCategoryIncome;
import com.netcracker.services.MonthReportService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class MonthReportServiceImplTest {

    @Mock
    private OperationDao operationDao;

    @Mock
    private MonthReportDao monthReportDao;

    @Mock
    private PersonalDebitAccountDao personalDebitAccountDao;

    @Mock
    private UserDao userDao;

    @Autowired
    private MonthReportService monthReportService;

    private MonthReport monthReport;


    private Collection<CategoryExpenseReport> categoryExpenseReport = new ArrayList<>();
    private Collection<CategoryIncomeReport> categoryIncomeReport = new ArrayList<>();

    private CategoryIncomeReport categoryIncomeReport1;
    private CategoryIncomeReport categoryIncomeReport2;
    private CategoryExpenseReport categoryExpenseReport1;
    private CategoryExpenseReport categoryExpenseReport2;
    private CategoryExpenseReport categoryExpenseReport3;


    private BigInteger testId;

    private User testUser;

    private LocalDate testLocalDate;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        testId = BigInteger.valueOf(1);

        categoryExpenseReport1 = new CategoryExpenseReport.Builder()
                .abstractCategoryReportId(BigInteger.valueOf(1))
                .amount(500L)
                .categoryExpense(CategoryExpense.CHILDREN)
                .userReference(testId)
                .build();

        categoryExpenseReport2 = new CategoryExpenseReport.Builder()
                .abstractCategoryReportId(BigInteger.valueOf(2))
                .amount(1000L)
                .categoryExpense(CategoryExpense.EDUCATION)
                .userReference(BigInteger.valueOf(98))
                .build();

        categoryExpenseReport3 = new CategoryExpenseReport.Builder()
                .abstractCategoryReportId(BigInteger.valueOf(3))
                .amount(300L)
                .categoryExpense(CategoryExpense.FOOD)
                .userReference(BigInteger.valueOf(117))
                .build();

        categoryIncomeReport1 = new CategoryIncomeReport.Builder()
                .abstractCategoryReportId(BigInteger.valueOf(4))
                .amount(900L)
                .categoryIncome(CategoryIncome.AWARD)
                .userReference(BigInteger.valueOf(1))
                .build();

        categoryIncomeReport2 = new CategoryIncomeReport.Builder()
                .abstractCategoryReportId(BigInteger.valueOf(4))
                .amount(1300L)
                .categoryIncome(CategoryIncome.SALARY)
                .userReference(BigInteger.valueOf(98))
                .build();
        categoryIncomeReport.add(categoryIncomeReport1);
        categoryIncomeReport.add(categoryIncomeReport2);

        categoryExpenseReport.add(categoryExpenseReport1);
        categoryExpenseReport.add(categoryExpenseReport2);
        categoryExpenseReport.add(categoryExpenseReport3);

        testLocalDate = LocalDate.of(2010, 12, 15);



        testUser = new User.Builder()
                .user_id(testId)
                .user_name("John")
                .build();

        monthReport = new MonthReport.Builder()
                .id(BigInteger.valueOf(10))
                .totalExpense(1200L)
                .totalIncome(1100L)
                .balance(1600L)
                .dateFrom(testLocalDate)
                .dateTo(testLocalDate)
                .categoryExpense(categoryExpenseReport)
                .categoryIncome(categoryIncomeReport)
                .build();
    }




    @Test
    public void convertPersonalToTxt2() {

        LocalDate dateFrom = LocalDate.of(2020,1,1);
        LocalDate dateTo = LocalDate.of(2020,2,1);
       // MonthReport monthReport = monthReportService.getMonthFamilyReport(BigInteger.valueOf(98), dateFrom);


        //monthReportService.convertToTxt(monthReport);
    }
}