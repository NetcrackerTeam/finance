package com.netcracker.dao.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.impl.mapper.CreditAccountFamilyMapper;
import com.netcracker.dao.impl.mapper.CreditAccountPersonalMapper;
import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.enums.CreditStatusPaid;
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

    @Override
    public void createPersonalCreditByDebitAccountId(BigInteger id, PersonalCreditAccount creditAccount) {
        jdbcTemplate.update(CREATE_PERSONAL_CREDIT_QUERY,
                creditAccount.getDate(),
                String.valueOf(creditAccount.getName()),
                String.valueOf(creditAccount.getAmount()),
                String.valueOf(creditAccount.getPaidAmount()),
                String.valueOf(creditAccount.getPaidAmount()),
                String.valueOf(creditAccount.getCreditRate()),
                creditAccount.getDateTo(),
                new BigDecimal(creditAccount.isPaid().getId()),
                new BigDecimal(id));
    }

    @Override
    public void createFamilyCreditByDebitAccountId(BigInteger id, FamilyCreditAccount creditAccount) {
        jdbcTemplate.update(CREATE_FAMILY_CREDIT_QUERY,
                creditAccount.getDate(),
                String.valueOf(creditAccount.getName()),
                String.valueOf(creditAccount.getAmount()),
                String.valueOf(creditAccount.getPaidAmount()),
                String.valueOf(creditAccount.getPaidAmount()),
                String.valueOf(creditAccount.getCreditRate()),
                creditAccount.getDateTo(),
                new BigDecimal(creditAccount.isPaid().getId()),
                new BigDecimal(id));
    }

    @Override
    public void updateIsPaidStatusPersonalCredit(BigInteger id, CreditStatusPaid statusPaid) {
        updatePaidStatus(id, statusPaid);
    }

    @Override
    public void updateIsPaidStatusFamilyCredit(BigInteger id, CreditStatusPaid statusPaid) {
        updatePaidStatus(id, statusPaid);
    }

    private void addCreditPayment(BigInteger id, long amount) {
        jdbcTemplate.update(UPDATE_CREDIT_PAYMENT_QUERY, String.valueOf(amount), new BigDecimal(id));
    }

    private void updatePaidStatus(BigInteger id, CreditStatusPaid statusPaid) {
        jdbcTemplate.update(UPDATE_ISPAID_STATUS_CREDIT_QUERY, String.valueOf(statusPaid.getId()), new BigDecimal(id));
    }
}
