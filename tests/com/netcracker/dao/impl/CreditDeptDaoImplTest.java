package com.netcracker.dao.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.dao.CreditDeptDao;
import com.netcracker.models.Debt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreditDeptDaoImplTest {

    private Debt personalDebtOne;
    private Debt familyDebtOne;

    @Autowired
    private CreditDeptDao creditDeptDao;

    @Before
    public void initializeObjects() {
        personalDebtOne = new Debt.Builder()
                .debtId(new BigInteger("14"))
                .amountDebt(0L)
                .dateFrom(Date.valueOf(LocalDate.of(2019, 11, 30)))
                .dateTo(Date.valueOf(LocalDate.of(2020, 2, 28)))
                .build();
        familyDebtOne = new Debt.Builder()
                .amountDebt(0L)
                .dateFrom(Date.valueOf(LocalDate.of(2019, 11, 30)))
                .dateTo(Date.valueOf(LocalDate.of(2020, 2, 28)))
                .debtId(new BigInteger("15"))
                .build();
    }

    @Test
    public void getPersonalDebtByCreditIdTest() {
        checkEqualsDebt(personalDebtOne, creditDeptDao.getPersonalDebtByCreditId(new BigInteger("10")));
    }

    @Test
    public void getFamilyDebtByCreditIdTest() {
        checkEqualsDebt(familyDebtOne, creditDeptDao.getFamilyDebtByCreditId(new BigInteger("11")));
    }

    @Test
    public void getPersonalDebtByIdTest() {
        checkEqualsDebt(personalDebtOne,
                creditDeptDao.getPersonalDebtById(personalDebtOne.getDebtId()));
    }

    @Test
    public void getFamilyDebtByIdTest() {
        checkEqualsDebt(familyDebtOne,
                creditDeptDao.getFamilyDebtById(familyDebtOne.getDebtId()));
    }

    @Rollback
    @Test
    public void updatePersonalDebtDateFromTest() {
        BigInteger accId = personalDebtOne.getDebtId();
        Date newDate = Date.valueOf(LocalDate.of(2019, 11, 30));

        creditDeptDao.updatePersonalDebtDateFrom(accId, newDate);

        assertEquals(newDate, creditDeptDao.getPersonalDebtById(accId).getDateFrom());
    }

    @Rollback
    @Test
    public void updateFamilyDebtDateFromTest() {
        BigInteger accId = familyDebtOne.getDebtId();
        Date newDate = Date.valueOf(LocalDate.of(2019, 11, 21));

        creditDeptDao.updateFamilyDebtDateFrom(accId, newDate);

        assertEquals(newDate, creditDeptDao.getPersonalDebtById(accId).getDateFrom());
    }

    @Rollback
    @Test
    public void updatePersonalDebtDateToTest() {
        BigInteger accId = personalDebtOne.getDebtId();
        Date newDate = Date.valueOf(LocalDate.of(2019, 11, 30));

        creditDeptDao.updatePersonalDebtDateTo(accId, newDate);

        assertEquals(newDate, creditDeptDao.getPersonalDebtById(accId).getDateTo());
    }

    @Rollback
    @Test
    public void updateFamilyDebtDateToTest() {
        BigInteger accId = familyDebtOne.getDebtId();
        Date newDate = Date.valueOf(LocalDate.of(2019, 11, 30));

        creditDeptDao.updateFamilyDebtDateTo(accId, newDate);

        assertEquals(newDate, creditDeptDao.getFamilyDebtById(accId).getDateTo());
    }

    @Rollback
    @Test
    public void updatePersonalDebtAmountTest() {
        BigInteger accId = personalDebtOne.getDebtId();
        long newAmount = personalDebtOne.getAmountDebt();

        creditDeptDao.updatePersonalDebtAmount(accId, newAmount);

        assertEquals(personalDebtOne.getAmountDebt(), creditDeptDao.getPersonalDebtById(accId).getAmountDebt());
    }

    @Rollback
    @Test
    public void updateFamilyDebtAmountTest() {
        BigInteger accId = familyDebtOne.getDebtId();
        long newAmount = familyDebtOne.getAmountDebt();

        creditDeptDao.updateFamilyDebtAmount(accId, newAmount);

        assertEquals(familyDebtOne.getAmountDebt(), creditDeptDao.getFamilyDebtById(accId).getAmountDebt());

    }

    private void checkEqualsDebt(Debt debtOne, Debt debtTwo) {
        assertEquals(debtOne.getDebtId(), debtTwo.getDebtId());
        assertEquals(debtOne.getDateFrom(), debtTwo.getDateFrom());
        assertEquals(debtOne.getDateTo(), debtTwo.getDateTo());
        assertEquals(debtOne.getAmountDebt(), debtTwo.getAmountDebt());
    }
}
