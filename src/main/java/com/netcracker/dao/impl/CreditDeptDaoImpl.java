package com.netcracker.dao.impl;

import com.netcracker.dao.CreditDeptDao;
import com.netcracker.dao.impl.mapper.CreditDebtMapper;
import com.netcracker.models.Debt;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class CreditDeptDaoImpl implements CreditDeptDao {

    private JdbcTemplate jdbcTemplate;

    public CreditDeptDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Debt getPersonalDebtByCreditId(BigInteger id) {
        return jdbcTemplate.queryForObject(SELECT_PERSONAL_DEBT_BY_CREDIT_ID_QUERY, new Object[]{new BigDecimal(id)}, new CreditDebtMapper());
    }

    @Override
    public Debt getFamilyDebtByCreditId(BigInteger id) {
        return jdbcTemplate.queryForObject(SELECT_FAMILY_DEBT_BY_CREDIT_ID_QUERY, new Object[]{new BigDecimal(id)}, new CreditDebtMapper());
    }

    @Override
    public Debt getPersonalDebtById(BigInteger id) {
        return jdbcTemplate.queryForObject(SELECT_DEBT_BY_ID_QUERY, new Object[]{new BigDecimal(id)}, new CreditDebtMapper());
    }

    @Override
    public Debt getFamilyDebtById(BigInteger id) {
        return jdbcTemplate.queryForObject(SELECT_DEBT_BY_ID_QUERY, new Object[]{new BigDecimal(id)}, new CreditDebtMapper());
    }

    @Override
    public void updatePersonalDebtDateFrom(BigInteger id, Date date) {
        jdbcTemplate.update(UPDATE_DEBT_DATE_FROM_QUERY, date, new BigDecimal(id));
    }

    @Override
    public void updateFamilyDebtDateFrom(BigInteger id, Date date) {
        jdbcTemplate.update(UPDATE_DEBT_DATE_FROM_QUERY, date, new BigDecimal(id));
    }

    @Override
    public void updatePersonalDebtDateTo(BigInteger id, Date date) {
        jdbcTemplate.update(UPDATE_DEBT_DATE_TO_QUERY, date, new BigDecimal(id));
    }

    @Override
    public void updateFamilyDebtDateTo(BigInteger id, Date date) {
        jdbcTemplate.update(UPDATE_DEBT_DATE_TO_QUERY, date, new BigDecimal(id));
    }

    @Override
    public void updatePersonalDebtAmount(BigInteger id, long amount) {
        jdbcTemplate.update(UPDATE_DEBT_AMOUNT_QUERY, String.valueOf(amount), new BigDecimal(id));
    }

    @Override
    public void updateFamilyDebtAmount(BigInteger id, long amount) {
        jdbcTemplate.update(UPDATE_DEBT_AMOUNT_QUERY, String.valueOf(amount), new BigDecimal(id));
    }

    @Override
    public void createPersonalDebtByCreditId(BigInteger id) {
        jdbcTemplate.update(CREATE_PERSONAL_DEBT_BY_CREDIT_ID, new BigDecimal(id));
    }

    @Override
    public void createFamilyDebtByCreditId(BigInteger id) {
        jdbcTemplate.update(CREATE_FAMILY_DEBT_BY_CREDIT_ID, new BigDecimal(id));
    }
}
