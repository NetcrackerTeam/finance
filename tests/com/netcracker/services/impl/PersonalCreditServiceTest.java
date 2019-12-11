package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.CreditDeptDao;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.models.AbstractCreditAccount;
import com.netcracker.models.Debt;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.services.utils.CreditUtils;
import com.netcracker.services.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

import static org.mockito.Mockito.*;

public class PersonalCreditServiceTest {

    @Mock
    private CreditAccountDao creditAccountDao;

    @Mock
    private CreditDeptDao creditDeptDao;

    @Mock
    private PersonalDebitAccountDao personalDebitAccountDao;

    @Mock
    private CreditOperationDao creditOperationDao;

    @InjectMocks
    private PersonalCreditServiceImpl creditService;

    private BigInteger testIdCredit;
    private BigInteger testIdAcc;
    private BigInteger testIdDebt;
    private long monthPayment;
    private Date today;

    private AbstractCreditAccount persCrAc;
    private PersonalDebitAccount personalDebitAccount;

    private PersonalDebitAccount personalDebitAccountNoEnoughMoney;
    private Debt debtEmpty;
    private Debt debtNotEmpty;

    private AbstractCreditAccount persCrAcWithDebt;
    private AbstractCreditAccount persCrAcNotEnoughMoneyNoDebt;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        LocalDate date = LocalDate.of(2018, 1, 2);
        testIdCredit = BigInteger.valueOf(1L);
        testIdAcc = BigInteger.valueOf(2L);
        testIdDebt = BigInteger.valueOf(3L);
        monthPayment = 1050;
        today = DateUtils.localDateToDate(LocalDate.now());

        debtEmpty = new Debt.Builder().debtId(testIdDebt).build();
        debtNotEmpty = new Debt.Builder()
                .debtId(testIdDebt)
                .amountDebt(2100L)
                .dateFrom(date)
                .dateTo(DateUtils.addMonthsToDate(date, 2))
                .build();

        persCrAc = new PersonalCreditAccount.Builder()
                .creditId(testIdCredit)
                .isPaid(CreditStatusPaid.NO)
                .date(date)
                .dateTo(DateUtils.addMonthsToDate(date, 5))
                .creditRate(12L)
                .paidAmount(1050L)
                .amount(5000L)
                .debtCredit(debtEmpty)
                .build();
        personalDebitAccount = new PersonalDebitAccount.Builder()
                .debitAmount(6000L)
                .build();
        personalDebitAccountNoEnoughMoney = new PersonalDebitAccount.Builder()
                .debitAmount(1000L)
                .build();
    }

    @Test
    public void addPersonalCreditPaymentAutoTest() {
        when(creditAccountDao.getPersonalCreditById(testIdCredit)).thenReturn((PersonalCreditAccount) persCrAc);
        when(personalDebitAccountDao.getPersonalAccountById(testIdAcc)).thenReturn(personalDebitAccount);

        creditService.addPersonalCreditPaymentAuto(testIdAcc, testIdCredit,
                CreditUtils.calculateMonthPayment(persCrAc.getDate(), persCrAc.getDateTo(),
                        persCrAc.getAmount(), persCrAc.getCreditRate()));

        verify(creditAccountDao, times(1)).updatePersonalCreditPayment(testIdCredit, persCrAc.getPaidAmount() + monthPayment);
        verify(creditOperationDao, times(1)).createPersonalCreditOperation(monthPayment, today, testIdCredit);
        verify(personalDebitAccountDao, times(1)).updateAmountOfPersonalAccount(testIdAcc, personalDebitAccount.getAmount() - monthPayment);
    }

    @Test
    public void addPersonalCreditPaymentAutoNoEnoughMoneyEmptyDebtTest() {
        when(creditAccountDao.getPersonalCreditById(testIdCredit)).thenReturn((PersonalCreditAccount) persCrAc);
        when(personalDebitAccountDao.getPersonalAccountById(testIdAcc)).thenReturn(personalDebitAccountNoEnoughMoney);

        creditService.addPersonalCreditPaymentAuto(testIdAcc, testIdCredit,
                CreditUtils.calculateMonthPayment(persCrAc.getDate(), persCrAc.getDateTo(),
                        persCrAc.getAmount(), persCrAc.getCreditRate()));

        verify(creditAccountDao, times(0)).updatePersonalCreditPayment(testIdCredit, persCrAc.getPaidAmount() + monthPayment);
        verify(creditOperationDao, times(0)).createPersonalCreditOperation(monthPayment, today, testIdCredit);
        verify(personalDebitAccountDao, times(0)).updateAmountOfPersonalAccount(testIdAcc, personalDebitAccount.getAmount() - monthPayment);
        verify(creditDeptDao, times(1)).updatePersonalDebtDateFrom(testIdDebt, today);
        verify(creditDeptDao, times(1)).updatePersonalDebtDateTo(testIdDebt,
                DateUtils.localDateToDate(DateUtils.addMonthsToDate(LocalDate.now(), 1)));
        verify(creditDeptDao, times(1)).updatePersonalDebtAmount(testIdDebt, monthPayment);


    }


}
