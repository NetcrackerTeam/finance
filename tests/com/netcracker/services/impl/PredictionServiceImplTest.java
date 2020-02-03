package com.netcracker.services.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.MonthReportDao;
import com.netcracker.models.CategoryExpenseReport;
import com.netcracker.models.CategoryIncomeReport;
import com.netcracker.models.MonthReport;

import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.models.enums.ReportCategoryExpense;
import com.netcracker.models.enums.ReportCategoryIncome;
import com.netcracker.services.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PredictionServiceImplTest {

    @Mock
    private MonthReportDao monthReportDao;

    @InjectMocks
    private PredictionServiceImpl predictionService;

    private MonthReport monthReport;

    private Collection<CategoryExpenseReport> categoryExpenseReport = new ArrayList<>();
    private Collection<CategoryIncomeReport> categoryIncomeReport = new ArrayList<>();

    private CategoryIncomeReport categoryIncomeReport1;
    private CategoryIncomeReport categoryIncomeReport2;
    private CategoryExpenseReport categoryExpenseReport1;
    private CategoryExpenseReport categoryExpenseReport2;
    private CategoryExpenseReport categoryExpenseReport3;

    private Date testLocalDate;

    private LocalDate testTime;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

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



        testLocalDate = DateUtils.localDateToDate(LocalDate.of(2010,12,15).atTime(0,0));

        monthReport = new MonthReport.Builder()
                .id(BigInteger.valueOf(10))
                .totalExpense(1200L)
                .totalIncome(1100L)
                .balance(1600L)
                .dateFrom(testTime.atTime(0,0))
                .dateTo(testTime.atTime(0,0))
                .categoryExpense(categoryExpenseReport)
                .categoryIncome(categoryIncomeReport)
                .build();
    }

    @Test
    public void predictCreditPossibility() {

    }

    @Test
    public void predictMonthIncome() {
//        predictionService.predictMonthIncome(BigInteger.valueOf(1),2);
//        when(monthReportDao.getMonthReportByPersonalAccountId(BigInteger.valueOf(2),testLocalDate,testLocalDate))
//                .thenReturn(monthReport);
//        verify(monthReportDao, times(6));
    }

    @Test
    public void predictMonthExpense() {
    }
}
