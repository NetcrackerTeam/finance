package com.netcracker.dao.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.impl.mapper.CreditAccountFamilyMapper;
import com.netcracker.dao.impl.mapper.CreditAccountPersonalMapper;
import com.netcracker.models.AbstractCreditAccount;
import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.enums.CreditStatusPaid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Repository
public class CreditAccountDaoImpl implements CreditAccountDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CreditAccountDaoImpl(DataSource dataSource) {
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
    public void updatePersonalCreditPayment(BigInteger id, double amount) {
        addCreditPayment(id, amount);
    }

    @Override
    public void updateFamilyCreditPayment(BigInteger id, double amount) {
        addCreditPayment(id, amount);
    }

    @Override
    public void createPersonalCreditByDebitAccountId(BigInteger id, PersonalCreditAccount creditAccount) {
        createCredit(id, creditAccount, CREATE_PERSONAL_CREDIT_QUERY, CREATE_PERSONAL_DEBT_BY_CREDIT_ID);
    }

    @Override
    public void createFamilyCreditByDebitAccountId(BigInteger id, FamilyCreditAccount creditAccount) {
        createCredit(id, creditAccount, CREATE_FAMILY_CREDIT_QUERY, CREATE_FAMILY_DEBT_BY_CREDIT_ID);
    }

    @Override
    public void updateIsPaidStatusPersonalCredit(BigInteger id, CreditStatusPaid statusPaid) {
        updatePaidStatus(id, statusPaid);
    }

    @Override
    public void updateIsPaidStatusFamilyCredit(BigInteger id, CreditStatusPaid statusPaid) {
        updatePaidStatus(id, statusPaid);
    }

    @Override
    public Collection<PersonalCreditAccount> getAllPersonCreditIdsByMonthDay(int day) {
        return jdbcTemplate.query(SELECT_ALL_CREDIT_PERSONAL_ID_BY_MONTH_DAY, new Object[]{day}, new CreditAccountPersonalMapper());
    }

    @Override
    public Collection<FamilyCreditAccount> getAllFamilyCreditIdsByMonthDay(int day) {
        return jdbcTemplate.query(SELECT_ALL_CREDIT_FAMILY_ID_BY_MONTH_DAY, new Object[]{day}, new CreditAccountFamilyMapper());
    }

    @Override
    public BigInteger getPersonalDebitIdByCreditId(BigInteger idCreditAccount) {
        return jdbcTemplate.queryForObject(GET_PERSONAL_DEBIT_ID_BY_CREDIT_ID, new Object[]{idCreditAccount}, BigInteger.class);
    }

    @Override
    public BigInteger getFamilyDebitIdByCreditId(BigInteger idCreditAccount) {
        return jdbcTemplate.queryForObject(GET_FAMILY_DEBIT_ID_BY_CREDIT_ID, new Object[]{idCreditAccount}, BigInteger.class);
    }

    private void createCredit(BigInteger id, AbstractCreditAccount creditAccount, String queryCredit, String queryDebt) {
        String creditName = id + creditAccount.getName();
        jdbcTemplate.update(queryCredit,
                creditName,
                Date.valueOf(creditAccount.getDate()),
                creditAccount.getName(),
                creditAccount.getAmount(),
                creditAccount.getPaidAmount(),
                creditAccount.getCreditRate(),
                Date.valueOf(creditAccount.getDateTo()),
                creditAccount.isPaid().getId(),
                creditAccount.getMonthDay(),
                id);
        BigInteger accountId = jdbcTemplate.queryForObject(SELECT_CREDIT_ID_BY_NAME, new Object[]{creditName}, BigInteger.class);
        jdbcTemplate.update(queryDebt, accountId);
    }

    private void addCreditPayment(BigInteger id, double amount) {
        jdbcTemplate.update(UPDATE_CREDIT_PAYMENT_QUERY, amount, id);
    }

    private void updatePaidStatus(BigInteger id, CreditStatusPaid statusPaid) {
        jdbcTemplate.update(UPDATE_ISPAID_STATUS_CREDIT_QUERY, statusPaid.getId(), id);
    }
}
