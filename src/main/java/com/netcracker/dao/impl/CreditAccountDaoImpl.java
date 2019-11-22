package com.netcracker.dao.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.impl.mapper.CreditAccountFamilyMapper;
import com.netcracker.dao.impl.mapper.CreditAccountPersonalMapper;
import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.PersonalCreditAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;

@Repository
public class CreditAccountDaoImpl implements CreditAccountDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public PersonalCreditAccount getPersonalCreditById(BigInteger id) {
        return jdbcTemplate.queryForObject(SELECT_PERSONAL_CREDIT_QUERY, new Object[]{id}, new CreditAccountPersonalMapper());
    }

    @Override
    public FamilyCreditAccount getFamilyCreditById(BigInteger id) {
        return jdbcTemplate.queryForObject(SELECT_FAMILY_CREDIT_QUERY, new Object[]{id}, new CreditAccountFamilyMapper());
    }

    @Override
    public List<PersonalCreditAccount> getAllPersonalCreditsByAccountId(BigInteger id) {
        return jdbcTemplate.query(SELECT_PERSONAL_CREDITS_BY_ACCOUNT_QUERY, new Object[]{id}, new CreditAccountPersonalMapper());
    }

    @Override
    public List<FamilyCreditAccount> getAllFamilyCreditsByAccountId(BigInteger id) {
        return jdbcTemplate.query(SELECT_FAMILY_CREDITS_BY_ACCOUNT_QUERY, new Object[]{id}, new CreditAccountFamilyMapper());
    }

    @Override
    public boolean addPersonalCreditPayment(BigInteger id, long amount) {
        return addCreditPayment(id, amount, ADD_PERSONAL_CREDIT_PAYMENT_QUERY) == 1;
    }

    @Override
    public boolean addFamilyCreditPayment(BigInteger id, long amount) {
        return addCreditPayment(id, amount, ADD_FAMILY_CREDIT_PAYMENT_QUERY) == 1;
    }

    private int addCreditPayment(BigInteger id, long amount, String query) {
        return jdbcTemplate.update(query, new Object[]{id, amount});
    }
}
