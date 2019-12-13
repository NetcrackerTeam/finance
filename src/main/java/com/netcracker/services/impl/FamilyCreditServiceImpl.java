package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.CreditDeptDao;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.exception.CreditAccountException;
import com.netcracker.exception.FamilyDebitAccountException;
import com.netcracker.models.*;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.services.FamilyCreditService;
import com.netcracker.services.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import static com.netcracker.services.utils.CreditUtils.getTotalCreditPayment;

@Service
public class FamilyCreditServiceImpl implements FamilyCreditService {
    private static final Logger logger = LoggerFactory.getLogger(FamilyCreditServiceImpl.class);


    @Autowired
    CreditAccountDao creditAccountDao;

    @Autowired
    CreditOperationDao creditOperationDao;

    @Autowired
    FamilyAccountDebitDao debitAccountDao;

    @Autowired
    CreditDeptDao creditDeptDao;

    @Override
    public void createFamilyCredit(BigInteger id, FamilyCreditAccount creditAccount) {
        creditAccountDao.createFamilyCreditByDebitAccountId(id, creditAccount);
    }

    @Override
    public void deleteFamilyCredit(BigInteger id) {
        creditAccountDao.updateIsPaidStatusFamilyCredit(id, CreditStatusPaid.YES);
    }

    @Override
    public void addFamilyCreditPayment(BigInteger idDebitAccount, BigInteger idCredit, long amount, Date date) {
        if (idDebitAccount != null) {
            if (idCredit != null) {
                FamilyCreditAccount creditAccount = getFamilyCreditAccount(idCredit);
                FamilyDebitAccount debitAccount = debitAccountDao.getFamilyAccountById(idDebitAccount);
                if (debitAccount != null) {
                    if (creditAccount != null) {
                        if (debitAccount.getAmount() < amount){
                            long remainToPay = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                                    creditAccount.getAmount(), creditAccount.getCreditRate()) - creditAccount.getPaidAmount();
                            if (remainToPay > amount)
                                addPayment(creditAccount, debitAccount, amount);
                            else {
                                logger.error("Remain to pay for credit {}. Wanted {}", remainToPay, amount);
                                throw new CreditAccountException("Left to pay less, then wanted", creditAccount);
                            }
                        } else {
                            logger.error("Not enough money on debit account by id = {}", idDebitAccount);
                            throw new CreditAccountException("Not enough money on debit account", creditAccount);
                        }
                    } else {
                        logger.error("Family credit account is null by id = {}", idCredit);
                        throw new CreditAccountException("Family credit account is null");
                    }
                } else {
                    logger.error("Family debit account is null by id = {}", idDebitAccount);
                    throw new FamilyDebitAccountException("Family debit account is null");
                }
            } else {
                logger.error("Variable idCredit is null");
                throw new IllegalArgumentException("Wrong input parameters");
            }
        } else {
            logger.error("Variable idDebitAccount is null");
            throw new IllegalArgumentException("Wrong input parameters");
        }
    }

    @Override
    public boolean addFamilyCreditPaymentAuto(BigInteger idDebitAccount, BigInteger idCredit, long amount) {
        if (idDebitAccount != null) {
            if (idCredit != null) {
                FamilyCreditAccount creditAccount = getFamilyCreditAccount(idCredit);
                FamilyDebitAccount debitAccount = debitAccountDao.getFamilyAccountById(idDebitAccount);
                if (debitAccount != null) {
                    if (creditAccount != null) {
                        if (creditAccount.getDate() != null && creditAccount.getDateTo() != null) {
                            long remainToPay = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                                    creditAccount.getAmount(), creditAccount.getCreditRate()) - creditAccount.getPaidAmount();
                            if (remainToPay < amount)
                                amount = remainToPay;
                            if (debitAccount.getAmount() < amount) {
                                logger.debug("Not enough money on debit account by id = {}. Needed more then {}", idDebitAccount, amount);
                                return false;
                            }
                            addPayment(creditAccount, debitAccount, amount);
                            logger.debug("Payment was completed successfully");
                            return true;
                        } else {
                            logger.error("Null field in family credit account by id = {}", idCredit);
                            throw new CreditAccountException("Account is not valid for operation", creditAccount);
                        }
                    } else {
                        logger.error("Family credit account is null by id = {}", idCredit);
                        throw new CreditAccountException("Family credit account is null");
                    }
                } else {
                    logger.error("Family debit account is null by id = {}", idDebitAccount);
                    throw new FamilyDebitAccountException("Family debit account is null");
                }
            } else {
                logger.error("Variable idCredit is null");
                throw new IllegalArgumentException("Wrong input parameters");
            }
        }
        logger.error("Variable idDebitAccount is null");
        throw new IllegalArgumentException("Wrong input parameters");
    }

    @Override
    public void increaseDebt(BigInteger idCredit, long amount) {
        if (idCredit != null) {
            FamilyCreditAccount creditAccount = creditAccountDao.getFamilyCreditById(idCredit);
            if (creditAccount != null) {
                Debt debt = creditAccount.getDebt();
                LocalDate newDateTo;
                if (debt != null) {
                    if (debt.getDebtId() != null) {
                        if (debt.getAmountDebt() == 0) {
                            debt.setDateFrom(LocalDate.now());
                            changeDebtDateFrom(debt.getDebtId(), debt.getDateFrom());
                            changeDebtAmount(debt.getDebtId(), amount);
                            newDateTo = DateUtils.addMonthsToDate(LocalDate.now(), 1);
                        } else {
                            newDateTo = DateUtils.addMonthsToDate(debt.getDateTo(), 1);
                            changeDebtAmount(debt.getDebtId(), debt.getAmountDebt() + amount);
                        }
                        changeDebtDateTo(debt.getDebtId(), newDateTo);
                        logger.debug("Debt increase was completed successfully");
                    } else {
                        logger.error("Debt id is null in family credit account by id = {}", idCredit);
                        throw new CreditAccountException("Debt is not valid for operation", creditAccount);
                    }
                } else {
                    logger.error("Debt is null in family credit account by id = {}", idCredit);
                    throw new CreditAccountException("Account  is not valid for operation");
                }
            } else {
                logger.error("Family credit account is null by id = {}", idCredit);
                throw new CreditAccountException("Account is not valid for operation");
            }
        } else {
            logger.error("Variable idCredit is null");
            throw new IllegalArgumentException("Wrong input parameters");
        }
    }

    @Override
    public void addAutoDebtRepayment(BigInteger idDebitAccount, BigInteger idCredit, long amount) {
        if (idDebitAccount != null) {
            if (idCredit != null) {
                FamilyCreditAccount creditAccount = getFamilyCreditAccount(idCredit);
                FamilyDebitAccount debitAccount = debitAccountDao.getFamilyAccountById(idDebitAccount);
                decreaseDebt(creditAccount.getDebt(), amount);
                addPayment(creditAccount, debitAccount, amount);
                logger.debug("Repayment was completed successfully");
            } else {
                logger.error("Variable idCredit is null");
                throw new IllegalArgumentException("Wrong input parameters");
            }
        } else {
            logger.error("Variable idDebitAccount is null");
            throw new IllegalArgumentException("Wrong input parameters");
        }
    }

    private void decreaseDebt(Debt debt, long amount) {
        if (debt != null) {
            if (debt.getDebtId() != null) {
                LocalDate newDateFrom = DateUtils.addMonthsToDate(debt.getDateFrom(), 1);
                if (newDateFrom.equals(debt.getDateTo())) {
                    changeDebtDateFrom(debt.getDebtId(), null);
                    changeDebtDateTo(debt.getDebtId(), null);
                    changeDebtAmount(debt.getDebtId(), 0);
                } else {
                    changeDebtDateFrom(debt.getDebtId(), newDateFrom);
                    changeDebtAmount(debt.getDebtId(), debt.getAmountDebt() - amount);
                }
            } else {
                logger.error("Null id in income debt");
                throw new CreditAccountException("Debt is not valid for operation");
            }
        } else {
            logger.error("Variable debt is null");
            throw new IllegalArgumentException("Wrong input parameters");
        }
    }

    @Override
    public Collection<FamilyCreditAccount> getFamilyCredits(BigInteger id) {
        return creditAccountDao.getAllFamilyCreditsByAccountId(id);
    }

    @Override
    public Collection<CreditOperation> getAllFamilyCreditOperations(BigInteger id) {
        return creditOperationDao.getAllCreditOperationsByCreditFamilyId(id);
    }

    @Override
    public FamilyCreditAccount getFamilyCreditAccount(BigInteger id) {
        return creditAccountDao.getFamilyCreditById(id);
    }

    private void createCreditOperation(BigInteger id, long amount, Date date) {
        creditOperationDao.createPersonalCreditOperation(amount, date, id);
    }

    public void changeDebtDateTo(BigInteger id, LocalDate date) {
        creditDeptDao.updateFamilyDebtDateTo(id, DateUtils.localDateToDate(date));
    }

    public void changeDebtDateFrom(BigInteger id, LocalDate date) {
        creditDeptDao.updatePersonalDebtDateFrom(id, DateUtils.localDateToDate(date));
    }

    public void changeDebtAmount(BigInteger id, long amount) {
        creditDeptDao.updatePersonalDebtAmount(id, amount);
    }

    private void addPayment(FamilyCreditAccount creditAccount, FamilyDebitAccount debitAccount, long amount) {
        long actualDebitAmount = debitAccount.getAmount();
        debitAccountDao.updateAmountOfFamilyAccount(debitAccount.getId(), actualDebitAmount - amount);
        creditOperationDao.createPersonalCreditOperation(amount, DateUtils.localDateToDate(LocalDate.now()), creditAccount.getCreditId());
        long updatedAmount = creditAccount.getPaidAmount() + amount;
        creditAccountDao.updateFamilyCreditPayment(creditAccount.getCreditId(), updatedAmount);
        long monthPayment = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                creditAccount.getAmount(), creditAccount.getCreditRate());
        if (monthPayment == updatedAmount) {
            creditAccountDao.updateIsPaidStatusFamilyCredit(creditAccount.getCreditId(), CreditStatusPaid.YES);
        }
    }

}
