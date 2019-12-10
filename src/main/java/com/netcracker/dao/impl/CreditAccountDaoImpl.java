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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
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
    public void updatePersonalCreditPayment(BigInteger id, long amount) {
        addCreditPayment(id, amount);
    }

    @Override
    public void updateFamilyCreditPayment(BigInteger id, long amount) {
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

    private void createCredit(BigInteger id, AbstractCreditAccount creditAccount, String queryCredit, String queryDebt) {
        String creditName = id + creditAccount.getName();
        jdbcTemplate.update(queryCredit,
                creditName,
                Date.valueOf(creditAccount.getDate()),
                creditAccount.getName(),
                String.valueOf(creditAccount.getAmount()),
                String.valueOf(creditAccount.getPaidAmount()),
                String.valueOf(creditAccount.getCreditRate()),
                Date.valueOf(creditAccount.getDateTo()),
                new BigDecimal(creditAccount.isPaid().getId()),
                String.valueOf(creditAccount.getMonthDay()),
                id);
        BigDecimal accountId = jdbcTemplate.queryForObject(SELECT_CREDIT_ID_BY_NAME, new Object[]{creditName}, BigDecimal.class);
        jdbcTemplate.update(queryDebt, accountId);
    }

    private void addCreditPayment(BigInteger id, long amount) {
        jdbcTemplate.update(UPDATE_CREDIT_PAYMENT_QUERY, String.valueOf(amount), id);
    }

    private void updatePaidStatus(BigInteger id, CreditStatusPaid statusPaid) {
        jdbcTemplate.update(UPDATE_ISPAID_STATUS_CREDIT_QUERY, String.valueOf(statusPaid.getId()), id);
    }
}
