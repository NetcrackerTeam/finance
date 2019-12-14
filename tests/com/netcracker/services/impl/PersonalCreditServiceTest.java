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
import com.netcracker.services.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
    private AbstractCreditAccount persCrAcWithDebtTwoMonth;
    private AbstractCreditAccount persCrAcWithDebtOneMonth;
    private AbstractCreditAccount persCrAcLastPay;

    private PersonalDebitAccount personalDebitAccount;
    private PersonalDebitAccount personalDebitAccountNoEnoughMoney;

    private Debt debtEmpty;
    private Debt debtNotEmptyTwoMonth;
    private Debt debtNotEmptyOneMonth;

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
        debtNotEmptyTwoMonth = new Debt.Builder()
                .debtId(testIdDebt)
                .amountDebt(2100L)
                .dateFrom(date)
                .dateTo(DateUtils.addMonthsToDate(date, 2))
                .build();
        debtNotEmptyOneMonth = new Debt.Builder()
                .debtId(testIdDebt)
                .amountDebt(2100L)
                .dateFrom(date)
                .dateTo(DateUtils.addMonthsToDate(date, 1))
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

        persCrAcWithDebtOneMonth = new PersonalCreditAccount.Builder()
                .creditId(testIdCredit)
                .isPaid(CreditStatusPaid.NO)
                .date(date)
                .dateTo(DateUtils.addMonthsToDate(date, 5))
                .creditRate(12L)
                .paidAmount(1050L)
                .amount(5000L)
                .debtCredit(debtNotEmptyOneMonth)
                .build();

        persCrAcWithDebtTwoMonth = new PersonalCreditAccount.Builder()
                .creditId(testIdCredit)
                .isPaid(CreditStatusPaid.NO)
                .date(date)
                .dateTo(DateUtils.addMonthsToDate(date, 5))
                .creditRate(12L)
                .paidAmount(1050L)
                .amount(5000L)
                .debtCredit(debtNotEmptyTwoMonth)
                .build();

        persCrAcLastPay = new PersonalCreditAccount.Builder()
                .creditId(testIdCredit)
                .isPaid(CreditStatusPaid.NO)
                .date(date)
                .dateTo(DateUtils.addMonthsToDate(date, 5))
                .creditRate(12L)
                .paidAmount(4200L)
                .amount(5000L)
                .debtCredit(debtNotEmptyOneMonth)
                .build();

        personalDebitAccount = new PersonalDebitAccount.Builder()
                .debitAmount(6000L)
                .debitId(testIdAcc)
                .build();
        personalDebitAccountNoEnoughMoney = new PersonalDebitAccount.Builder()
                .debitAmount(1000L)
                .debitId(testIdAcc)
                .build();
    }

    @Test
    public void addPersonalCreditPaymentAutoTest() {
        when(creditAccountDao.getPersonalCreditById(testIdCredit)).thenReturn((PersonalCreditAccount) persCrAc);
        when(personalDebitAccountDao.getPersonalAccountById(testIdAcc)).thenReturn(personalDebitAccount);

        assertTrue(creditService.addPersonalCreditPaymentAuto(testIdAcc, testIdCredit, monthPayment));

        verify(creditAccountDao, times(1))
                .updatePersonalCreditPayment(testIdCredit, persCrAc.getPaidAmount() + monthPayment);

        verify(creditOperationDao, times(1))
                .createPersonalCreditOperation(monthPayment, today, testIdCredit);

        verify(personalDebitAccountDao, times(1))
                .updateAmountOfPersonalAccount(testIdAcc, personalDebitAccount.getAmount() - monthPayment);
    }


    @Test
    public void addPersonalCreditPaymentAutoNoEnoughMoney() {
        when(creditAccountDao.getPersonalCreditById(testIdCredit)).thenReturn((PersonalCreditAccount) persCrAc);
        when(personalDebitAccountDao.getPersonalAccountById(testIdAcc)).thenReturn(personalDebitAccountNoEnoughMoney);

        assertFalse(creditService.addPersonalCreditPaymentAuto(testIdAcc, testIdCredit, monthPayment));
    }

    @Test
    public void increaseDebtWhenHasTest() {
        when(creditAccountDao.getPersonalCreditById(testIdCredit)).thenReturn((PersonalCreditAccount) persCrAcWithDebtTwoMonth);

        creditService.increaseDebt(testIdCredit, monthPayment);

        verify(creditDeptDao, times(1))
                .updatePersonalDebtDateTo(testIdDebt,
                        DateUtils.localDateToDate(LocalDate.of(2018, 4, 2)));

        verify(creditDeptDao, times(1))
                .updatePersonalDebtAmount(testIdDebt, 3150);
    }

    @Test
    public void increaseDebtWithoutPrevTest() {
        when(creditAccountDao.getPersonalCreditById(testIdCredit)).thenReturn((PersonalCreditAccount) persCrAc);

        creditService.increaseDebt(testIdCredit, monthPayment);

        verify(creditDeptDao, times(1))
                .updatePersonalDebtDateFrom(testIdDebt, DateUtils.localDateToDate(LocalDate.now()));

        verify(creditDeptDao, times(1))
                .updatePersonalDebtDateTo(testIdDebt,
                        DateUtils.localDateToDate(DateUtils.addMonthsToDate(LocalDate.now(), 1)));

        verify(creditDeptDao, times(1))
                .updatePersonalDebtAmount(testIdDebt, monthPayment);
    }

    @Test
    public void addAutoDebtRepaymentTest() {
        when(creditAccountDao.getPersonalCreditById(testIdCredit)).thenReturn((PersonalCreditAccount) persCrAcWithDebtTwoMonth);
        when(personalDebitAccountDao.getPersonalAccountById(testIdAcc)).thenReturn(personalDebitAccount);

        creditService.addAutoDebtRepayment(testIdAcc, testIdCredit, monthPayment);

        verify(creditAccountDao, times(1))
                .updatePersonalCreditPayment(testIdCredit, persCrAcWithDebtTwoMonth.getPaidAmount() + monthPayment);

        verify(creditOperationDao, times(1))
                .createPersonalCreditOperation(monthPayment, today, testIdCredit);

        verify(personalDebitAccountDao, times(1))
                .updateAmountOfPersonalAccount(testIdAcc, personalDebitAccount.getAmount() - monthPayment);

        verify(creditDeptDao, times(1))
                .updatePersonalDebtDateFrom(testIdDebt, DateUtils.localDateToDate(LocalDate.of(2018, 2, 2)));

        verify(creditDeptDao, times(1))
                .updatePersonalDebtAmount(testIdDebt, 1050);

    }

    @Test
    public void addAutoDebtRepaymentCloseDebtTest() {
        when(creditAccountDao.getPersonalCreditById(testIdCredit)).thenReturn((PersonalCreditAccount) persCrAcWithDebtOneMonth);
        when(personalDebitAccountDao.getPersonalAccountById(testIdAcc)).thenReturn(personalDebitAccount);

        creditService.addAutoDebtRepayment(testIdAcc, testIdCredit, monthPayment);

        verify(creditAccountDao, times(1))
                .updatePersonalCreditPayment(testIdCredit, persCrAcWithDebtOneMonth.getPaidAmount() + monthPayment);

        verify(creditOperationDao, times(1))
                .createPersonalCreditOperation(monthPayment, today, testIdCredit);

        verify(personalDebitAccountDao, times(1))
                .updateAmountOfPersonalAccount(testIdAcc, personalDebitAccount.getAmount() - monthPayment);

        verify(creditDeptDao, times(1))
                .updatePersonalDebtDateFrom(testIdDebt, null);

        verify(creditDeptDao, times(1))
                .updatePersonalDebtDateTo(testIdDebt, null);

        verify(creditDeptDao, times(1))
                .updatePersonalDebtAmount(testIdDebt, 0);
    }

    @Test
    public void addAutoDebtRepaymentCloseCreditTest() {
        when(creditAccountDao.getPersonalCreditById(testIdCredit)).thenReturn((PersonalCreditAccount) persCrAcLastPay);
        when(personalDebitAccountDao.getPersonalAccountById(testIdAcc)).thenReturn(personalDebitAccount);

        creditService.addAutoDebtRepayment(testIdAcc, testIdCredit, monthPayment);

        verify(creditAccountDao, times(1))
                .updatePersonalCreditPayment(testIdCredit, persCrAcLastPay.getPaidAmount() + monthPayment);

        verify(creditOperationDao, times(1))
                .createPersonalCreditOperation(monthPayment, today, testIdCredit);

        verify(personalDebitAccountDao, times(1))
                .updateAmountOfPersonalAccount(testIdAcc, personalDebitAccount.getAmount() - monthPayment);

        verify(creditDeptDao, times(1))
                .updatePersonalDebtDateFrom(testIdDebt, null);

        verify(creditDeptDao, times(1))
                .updatePersonalDebtDateTo(testIdDebt, null);

        verify(creditDeptDao, times(1))
                .updatePersonalDebtAmount(testIdDebt, 0);

        verify(creditAccountDao, times(1))
                .updateIsPaidStatusPersonalCredit(testIdCredit, CreditStatusPaid.YES);
    }
}
