package com.netcracker.dao.impl;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.dao.impl.mapper.MonthReportMapper;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.MonthReport;
import com.netcracker.models.PersonalDebitAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
@Component
public class MonthReportDaoImpl implements MonthReportDao {

    protected JdbcTemplate template;

    @Autowired
    public MonthReportDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void createPersonalMonthReport(MonthReport monthReport) {
        template.update(CREATE_PERSONAL_MONTH_REPORT, new Object[]{
                monthReport.getBalance().toString(),
                monthReport.getTotalExpense().toString(),
                monthReport.getTotalIncome().toString(),
                monthReport.getDate_from(),
                monthReport.getDate_to()
        });
    }

    @Override
    public void createFamilyMonthReport(MonthReport monthReport) {
        template.update(CREATE_FAMILY_MONTH_REPORT, new Object[]{
                monthReport.getTotalIncome().toString(),
                monthReport.getTotalExpense().toString(),
                monthReport.getBalance().toString(),
                monthReport.getDate_from(),
                monthReport.getDate_to()
        });
    }

    @Override
    public void deletePersonalMonthReport(BigInteger id) {
        template.update(DELETE_PERSONAL_MONTH_REPORT, new Object[]{new BigDecimal(id)});
    }

    @Override
    public void deleteFamilyMonthReport(BigInteger id) {
        template.update(DELETE_FAMILY_MONTH_REPORT, new Object[]{new BigDecimal(id)});
    }

    @Override
    public MonthReport getMonthReportByFamilyAccountId(BigInteger id) {
        return template.queryForObject(GET_MONTH_REPORT_BY_PERSONAL_ACCOUNT_ID,
                new Object[]{new BigDecimal(id)}, new MonthReportMapper());
    }

    @Override
    public MonthReport getMonthReportByPersonalAccountId(BigInteger id) {
        return template.queryForObject(GET_MONTH_REPORT_BY_FAMILY_ACCOUNT_ID,
                new Object[]{new BigDecimal(id)}, new MonthReportMapper());
    }
}
