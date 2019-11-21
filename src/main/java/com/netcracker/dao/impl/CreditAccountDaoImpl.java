package com.netcracker.dao.impl;

import com.netcracker.dao.CreditAccountDao;
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
        return null;
    }

    @Override
    public FamilyCreditAccount getFamilyCreditById(BigInteger id) {
        return null;
    }

    @Override
    public List<PersonalCreditAccount> getAllPersonalCreditsByAccountId(BigInteger id) {
        return null;
    }

    @Override
    public List<FamilyCreditAccount> getAllFamilyCreditsByAccountId(BigInteger id) {
        return null;
    }

    @Override
    public boolean addPersonalCreditPayment(BigInteger id, long amount) {
        return false;
    }

    @Override
    public boolean addFamilyCreditPayment(BigInteger id, long amount) {
        return false;
    }
}
