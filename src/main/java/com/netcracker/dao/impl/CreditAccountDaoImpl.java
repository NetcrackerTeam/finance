package com.netcracker.dao.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.impl.mapper.CreditAccountFamilyMapper;
import com.netcracker.dao.impl.mapper.CreditAccountPersonalMapper;
import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.PersonalCreditAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Component
public class CreditAccountDaoImpl implements CreditAccountDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CreditAccountDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public PersonalCreditAccount getPersonalCreditById(BigInteger id) {
        return jdbcTemplate.queryForObject(SELECT_PERSONAL_CREDIT_QUERY, new Object[]{new BigDecimal(id)}, new CreditAccountPersonalMapper());
    }

    @Override
    public FamilyCreditAccount getFamilyCreditById(BigInteger id) {
        return jdbcTemplate.queryForObject(SELECT_FAMILY_CREDIT_QUERY, new Object[]{new BigDecimal(id)}, new CreditAccountFamilyMapper());
    }

    @Override
    public List<PersonalCreditAccount> getAllPersonalCreditsByAccountId(BigInteger id) {
        return jdbcTemplate.query(SELECT_PERSONAL_CREDITS_BY_ACCOUNT_QUERY, new Object[]{new BigDecimal(id)}, new CreditAccountPersonalMapper());
    }

    @Override
    public List<FamilyCreditAccount> getAllFamilyCreditsByAccountId(BigInteger id) {
        return jdbcTemplate.query(SELECT_FAMILY_CREDITS_BY_ACCOUNT_QUERY, new Object[]{new BigDecimal(id)}, new CreditAccountFamilyMapper());
    }

    @Override
    public void updatePersonalCreditPayment(BigInteger id, long amount) {
        addCreditPayment(id, amount);
    }

    @Override
    public void updateFamilyCreditPayment(BigInteger id, long amount) {
        addCreditPayment(id, amount);
    }

    private void addCreditPayment(BigInteger id, long amount) {
        jdbcTemplate.update(UPDATE_CREDIT_PAYMENT_QUERY, String.valueOf(amount), new BigDecimal(id));
    }
}
