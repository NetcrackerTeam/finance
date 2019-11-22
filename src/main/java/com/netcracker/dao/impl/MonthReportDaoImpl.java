package com.netcracker.dao.impl;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.PersonalDebitAccount;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class MonthReportDaoImpl implements MonthReportDao {

    protected JdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void createPersonalMonthReport(PersonalDebitAccount personalDebitAccount) {

    }

    @Override
    public void createFamilyMonthReport(FamilyDebitAccount familyDebitAccount) {

    }

    @Override
    public void deletePersonalMonthReport() {

    }

    @Override
    public void deleteFamilyMonthReport() {

    }

    @Override
    public void getMonthReportByFamilyAccountId() {

    }

    @Override
    public void getMonthReportByPersonalAccountId() {

    }
}
