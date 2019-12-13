package com.netcracker.services.impl;

import com.netcracker.dao.CreditAccountDao;
import com.netcracker.dao.CreditDeptDao;
import com.netcracker.dao.CreditOperationDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.exception.CreditAccountException;
import com.netcracker.models.CreditOperation;
import com.netcracker.models.Debt;
import com.netcracker.models.PersonalCreditAccount;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.enums.CreditStatusPaid;
import com.netcracker.services.PersonalCreditService;
import com.netcracker.services.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;

import static com.netcracker.services.utils.CreditUtils.getTotalCreditPayment;

@Service
public class PersonalCreditServiceImpl implements PersonalCreditService {
    private static final Logger logger = LoggerFactory.getLogger(PersonalCreditServiceImpl.class);

    @Autowired
    CreditAccountDao creditAccountDao;

    @Autowired
    CreditOperationDao creditOperationDao;

    @Autowired
    PersonalDebitAccountDao debitAccountDao;

    @Autowired
    CreditDeptDao creditDeptDao;

    @Override
    public void createPersonalCredit(BigInteger id, PersonalCreditAccount creditAccount) {
        creditAccountDao.createPersonalCreditByDebitAccountId(id, creditAccount);
    }

    @Override
    public void deletePersonalCredit(BigInteger id) {
        creditAccountDao.updateIsPaidStatusPersonalCredit(id, CreditStatusPaid.YES);
    }

    @Override
    public void addPersonalCreditPayment(BigInteger idDebitAccount, BigInteger idCredit, long amount) {
        if (idDebitAccount != null) {
            if (idCredit != null) {
                PersonalCreditAccount creditAccount = getPersonalCreditAccount(idCredit);
                PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idDebitAccount);
                if (debitAccount != null) {
                    if (creditAccount != null) {
                        if (debitAccount.getAmount() < amount) {
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
                        logger.error("Personal credit account is null by id = {}", idCredit);
                        throw new CreditAccountException("Personal credit account is null");
                    }
                } else {
                    logger.error("Personal debit account is null by id = {}", idDebitAccount);
                    //ToDo: throw new Personal debit exception
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
    public boolean addPersonalCreditPaymentAuto(BigInteger idDebitAccount, BigInteger idCredit, long amount) {
        if (idDebitAccount != null) {
            if (idCredit != null) {
                PersonalCreditAccount creditAccount = getPersonalCreditAccount(idCredit);
                PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idDebitAccount);
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
                            logger.error("Null field in personal credit account by id = {}", idCredit);
                            throw new CreditAccountException("Account is not valid for operation", creditAccount);
                        }
                    } else {
                        logger.error("Personal credit account is null by id = {}", idCredit);
                        throw new CreditAccountException("Personal credit account is null");
                    }
                } else {
                    logger.error("Personal debit account is null by id = {}", idDebitAccount);
                    //ToDo: throw new Personal debit exception
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
            PersonalCreditAccount creditAccount = creditAccountDao.getPersonalCreditById(idCredit);
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
                        logger.error("Debt id is null in credit account by id = {}", idCredit);
                        throw new CreditAccountException("Debt is not valid for operation", creditAccount);
                    }
                } else {
                    logger.error("Debt is null in personal credit account by id = {}", idCredit);
                    throw new CreditAccountException("Account  is not valid for operation");
                }
            } else {
                logger.error("Personal credit account is null by id = {}", idCredit);
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
                PersonalCreditAccount creditAccount = getPersonalCreditAccount(idCredit);
                PersonalDebitAccount debitAccount = debitAccountDao.getPersonalAccountById(idDebitAccount);
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
    public Collection<PersonalCreditAccount> getPersonalCredits(BigInteger id) {
        return creditAccountDao.getAllPersonalCreditsByAccountId(id);
    }

    @Override
    public Collection<CreditOperation> getAllPersonalCreditOperations(BigInteger id) {
        return creditOperationDao.getAllCreditOperationsByCreditPersonalId(id);
    }

    @Override
    public PersonalCreditAccount getPersonalCreditAccount(BigInteger id) {
        return creditAccountDao.getPersonalCreditById(id);
    }

    private void addPayment(PersonalCreditAccount creditAccount, PersonalDebitAccount debitAccount, long amount) {
        long actualDebitAmount = debitAccount.getAmount();
        debitAccountDao.updateAmountOfPersonalAccount(debitAccount.getId(), actualDebitAmount - amount);
        creditOperationDao.createPersonalCreditOperation(amount, DateUtils.localDateToDate(LocalDate.now()), creditAccount.getCreditId());
        long updatedAmount = creditAccount.getPaidAmount() + amount;
        creditAccountDao.updatePersonalCreditPayment(creditAccount.getCreditId(), updatedAmount);
        long monthPayment = getTotalCreditPayment(creditAccount.getDate(), creditAccount.getDateTo(),
                creditAccount.getAmount(), creditAccount.getCreditRate());
        if (monthPayment == updatedAmount) {
            creditAccountDao.updateIsPaidStatusPersonalCredit(creditAccount.getCreditId(), CreditStatusPaid.YES);
        }
    }

    public void changeDebtDateTo(BigInteger id, LocalDate date) {
        creditDeptDao.updatePersonalDebtDateTo(id, DateUtils.localDateToDate(date));
    }

    public void changeDebtDateFrom(BigInteger id, LocalDate date) {
        creditDeptDao.updatePersonalDebtDateFrom(id, DateUtils.localDateToDate(date));
    }

    public void changeDebtAmount(BigInteger id, long amount) {
        creditDeptDao.updatePersonalDebtAmount(id, amount);
    }

}
