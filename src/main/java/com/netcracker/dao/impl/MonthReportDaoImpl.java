package com.netcracker.dao.impl;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.dao.impl.mapper.MonthReportMapper;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.MonthReport;
import com.netcracker.models.PersonalDebitAccount;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;

public class MonthReportDaoImpl implements MonthReportDao {

    protected JdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void createPersonalMonthReport(PersonalDebitAccount personalDebitAccount) {
        template.update(CREATE_PERSONAL_MONTH_REPORT, new Object[]{personalDebitAccount});
    }

    @Override
    public void createFamilyMonthReport(FamilyDebitAccount familyDebitAccount) {
        template.update(CREATE_FAMILY_MONTH_REPORT, new Object[]{familyDebitAccount});
    }

    @Override
    public void deletePersonalMonthReport(BigInteger id) {
        template.update(DELETE_PERSONAL_MONTH_REPORT, new Object[]{id});
    }

    @Override
    public void deleteFamilyMonthReport(BigInteger id) {
        template.update(DELETE_FAMILY_MONTH_REPORT, new Object[]{id});
    }

    @Override
    public MonthReport getMonthReportByFamilyAccountId(BigInteger id) {
        return template.queryForObject(GET_MONTH_REPORT_BY_PERSONAL_ACCOUNT_ID,
                new Object[]{id}, new MonthReportMapper());
    }

    @Override
    public MonthReport getMonthReportByPersonalAccountId(BigInteger id) {
        return template.queryForObject(GET_MONTH_REPORT_BY_FAMILY_ACCOUNT_ID,
                new Object[]{id}, new MonthReportMapper());
    }
}
