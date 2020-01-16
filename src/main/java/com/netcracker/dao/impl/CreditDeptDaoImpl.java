package com.netcracker.dao.impl;

import com.netcracker.dao.CreditDeptDao;
import com.netcracker.dao.impl.mapper.CreditDebtMapper;
import com.netcracker.models.Debt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Date;

@Repository
public class CreditDeptDaoImpl implements CreditDeptDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CreditDeptDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void updatePersonalDebtDateFrom(BigInteger id, Date date) {
        jdbcTemplate.update(UPDATE_DEBT_DATE_FROM_QUERY, date, id);
    }

    @Override
    public void updateFamilyDebtDateFrom(BigInteger id, Date date) {
        jdbcTemplate.update(UPDATE_DEBT_DATE_FROM_QUERY, date, id);
    }

    @Override
    public void updatePersonalDebtDateTo(BigInteger id, Date date) {
        jdbcTemplate.update(UPDATE_DEBT_DATE_TO_QUERY, date, id);
    }

    @Override
    public void updateFamilyDebtDateTo(BigInteger id, Date date) {
        jdbcTemplate.update(UPDATE_DEBT_DATE_TO_QUERY, date, id);
    }

    @Override
    public void updatePersonalDebtAmount(BigInteger id, double amount) {
        jdbcTemplate.update(UPDATE_DEBT_AMOUNT_QUERY, amount, id);
    }

    @Override
    public void updateFamilyDebtAmount(BigInteger id, double amount) {
        jdbcTemplate.update(UPDATE_DEBT_AMOUNT_QUERY, amount, id);
    }
}
