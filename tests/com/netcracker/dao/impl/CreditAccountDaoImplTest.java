package com.netcracker.dao.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.CreditAccountDao;
import com.netcracker.models.AbstractCreditAccount;
import com.netcracker.models.Debt;
import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.enums.CreditStatusPaid;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class CreditAccountDaoImplTest {

    private AbstractCreditAccount personalCreditAccountOne;
    private AbstractCreditAccount familyCreditAccountOne;
    private List<AbstractCreditAccount> personalListCredits;
    private List<AbstractCreditAccount> familyListCredits;

    @Autowired
    private CreditAccountDao creditAccountDao;

    @Before
    public void initializeObjects() {
        Locale.setDefault(Locale.ENGLISH);
        personalListCredits = new ArrayList<>();
        familyListCredits = new ArrayList<>();
        Debt famDebtOne = new Debt.Builder()
                .amountDebt(0L)
                .dateFrom(Date.valueOf(LocalDate.of(2019, 11, 29)))
                .dateTo(Date.valueOf(LocalDate.of(2020, 2, 27)))
                .debtId(new BigInteger("15"))
                .build();

        Debt perDebtOne = new Debt.Builder()
                .debtId(new BigInteger("14"))
                .amountDebt(0L)
                .dateFrom(Date.valueOf(LocalDate.of(2019, 11, 29)))
                .dateTo(Date.valueOf(LocalDate.of(2020, 2, 27)))
                .build();

        familyCreditAccountOne =
                new FamilyCreditAccount.Builder()
                        .creditId(new BigInteger("11"))
                        .name("Credit_Money1")
                        .amount(15000L)
                        .paidAmount(3000L)
                        .creditRate(20L)
                        .date(LocalDate.of(2019, 11, 29))
                        .dateTo(LocalDate.of(2020, 2, 27))
                        .monthDay(1)
                        .isPaid(CreditStatusPaid.getStatusByKey(new BigInteger("38")))
                        .debtCredit(famDebtOne)
                        .build();

        personalCreditAccountOne =
                new PersonalCreditAccount.Builder()
                        .creditId(new BigInteger("10"))
                        .name("Credit_Money1")
                        .amount(10000L)
                        .paidAmount(2000L)
                        .date(LocalDate.of(2019, 11, 29))
                        .creditRate(20L)
                        .dateTo(LocalDate.of(2020, 2, 27))
                        .monthDay(1)
                        .isPaid(CreditStatusPaid.getStatusByKey(new BigInteger("38")))
                        .debtCredit(perDebtOne)
                        .build();

        personalListCredits.add(personalCreditAccountOne);
        familyListCredits.add(familyCreditAccountOne);

    }

    @Test
    public void getPersonalCreditByIdTest() {
        checkEqualsCredit(personalCreditAccountOne, creditAccountDao.getPersonalCreditById(new BigInteger("10")));
    }

    @Test
    public void getFamilyCreditByIdTest() {
        checkEqualsCredit(familyCreditAccountOne, creditAccountDao.getFamilyCreditById(new BigInteger("11")));
    }

    @Test
    public void getAllPersonalCreditByAccountId() {
        List<PersonalCreditAccount> listResult = creditAccountDao.getAllPersonalCreditsByAccountId(new BigInteger("2"));
        assertEquals(personalListCredits.size(), listResult.size());
        for (int i = 0; i < personalListCredits.size(); i++) {
            checkEqualsCredit(personalListCredits.get(i), listResult.get(i));
        }
    }

    @Test
    public void getAllFamilyCreditByAccountId() {
        List<FamilyCreditAccount> listResult = creditAccountDao.getAllFamilyCreditsByAccountId(new BigInteger("3"));
        for (int i = 0; i < familyListCredits.size(); i++) {
            checkEqualsCredit(familyListCredits.get(i), listResult.get(i));
        }
    }

    @Test
    public void updatePersonalCreditPaymentTest() {
        long newAmount = personalCreditAccountOne.getPaidAmount();
        BigInteger accId = personalCreditAccountOne.getCreditId();

        creditAccountDao.updatePersonalCreditPayment(accId, newAmount);

        assertEquals(personalCreditAccountOne.getAmount(), creditAccountDao.getPersonalCreditById(accId).getAmount());
    }

    @Test
    public void updateFamilyCreditPayment() {
        long newAmount = familyCreditAccountOne.getPaidAmount();
        BigInteger accId = familyCreditAccountOne.getCreditId();

        creditAccountDao.updateFamilyCreditPayment(accId, newAmount);

        assertEquals(familyCreditAccountOne.getAmount(), creditAccountDao.getFamilyCreditById(accId).getAmount());
    }

    @Test
    public void updateIsPaidStatusPersonalCreditTest() {
        CreditStatusPaid statusPaid = CreditStatusPaid.NO;
        BigInteger creditId = personalCreditAccountOne.getCreditId();

        creditAccountDao.updateIsPaidStatusPersonalCredit(creditId, statusPaid);
        PersonalCreditAccount result = creditAccountDao.getPersonalCreditById(creditId);

        assertEquals(statusPaid, result.isPaid());
    }

    @Test
    public void updateIsPaidStatusFamilyCreditTest() {
        CreditStatusPaid statusPaid = CreditStatusPaid.NO;
        BigInteger creditId = familyCreditAccountOne.getCreditId();

        creditAccountDao.updateIsPaidStatusFamilyCredit(creditId, statusPaid);
        FamilyCreditAccount result = creditAccountDao.getFamilyCreditById(creditId);

        assertEquals(statusPaid, result.isPaid());
    }

    private void checkEqualsCredit(AbstractCreditAccount creditOne, AbstractCreditAccount creditTwo) {
        assertEquals(creditOne.getCreditId(), creditTwo.getCreditId());
        assertEquals(creditOne.getName(), creditTwo.getName());
        assertEquals(creditOne.getAmount(), creditTwo.getAmount());
        assertEquals(creditOne.getPaidAmount(), creditTwo.getPaidAmount());
        assertEquals(creditOne.getDate(), creditTwo.getDate());
        assertEquals(creditOne.getCreditRate(), creditTwo.getCreditRate());
        assertEquals(creditOne.getDateTo(), creditTwo.getDateTo());
        assertEquals(creditOne.getMonthDay(), creditTwo.getMonthDay());
        assertEquals(creditOne.isPaid(), creditTwo.isPaid());
        checkEqualsDebt(creditOne.getDebt(), creditTwo.getDebt());
    }

    private void checkEqualsDebt(Debt debtOne, Debt debtTwo) {
        assertEquals(debtOne.getDebtId(), debtTwo.getDebtId());
        assertEquals(debtOne.getDateFrom(), debtTwo.getDateFrom());
        assertEquals(debtOne.getDateTo(), debtTwo.getDateTo());
        assertEquals(debtOne.getAmountDebt(), debtTwo.getAmountDebt());
    }
}
