package com.netcracker.services.impl;


import com.netcracker.dao.*;
import com.netcracker.models.CategoryExpenseReport;
import com.netcracker.models.CategoryIncomeReport;
import com.netcracker.models.MonthReport;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.services.MonthReportService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.mockito.Mockito.*;


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


    private LocalDate testLocalDate;





    @Before
    public void init(){
       // MockitoAnnotations.initMocks(this);



        categoryExpenseReport1 = new CategoryExpenseReport.Builder()
                .abstractCategoryReportId(BigInteger.valueOf(1))
                .amount(500L)
                .categoryExpense(CategoryExpense.CHILDREN)
                .build();

        categoryExpenseReport2 = new CategoryExpenseReport.Builder()
                .abstractCategoryReportId(BigInteger.valueOf(2))
                .amount(1000L)
                .categoryExpense(CategoryExpense.EDUCATION)
                .build();

        categoryExpenseReport3 = new CategoryExpenseReport.Builder()
                .abstractCategoryReportId(BigInteger.valueOf(3))
                .amount(300L)
                .categoryExpense(CategoryExpense.FOOD)
                .build();

        categoryIncomeReport1 = new CategoryIncomeReport.Builder()
                .abstractCategoryReportId(BigInteger.valueOf(4))
                .amount(900L)
                .categoryIncome(CategoryIncome.AWARD)
                .build();

        categoryIncomeReport2 = new CategoryIncomeReport.Builder()
                .abstractCategoryReportId(BigInteger.valueOf(4))
                .amount(1300L)
                .categoryIncome(CategoryIncome.SALARY)
                .build();
        categoryIncomeReport.add(categoryIncomeReport1);
        categoryIncomeReport.add(categoryIncomeReport2);

        categoryExpenseReport.add(categoryExpenseReport1);
        categoryExpenseReport.add(categoryExpenseReport2);
        categoryExpenseReport.add(categoryExpenseReport3);

        testLocalDate =  LocalDate.of(2010,12,15);

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
    public void convertToTxt() {
        monthReportService.convertFamilyToTxt(monthReport);
    }

}
