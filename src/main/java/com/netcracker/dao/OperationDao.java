package com.netcracker.dao;

import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OperationDao {

    String ADD_INCOME_PERSONAL_BY_ACCOUNT_ID = null;
    String ADD_EXPENSE_PERSONAL_BY_ACCOUNT_ID = null;

    String ADD_INCOME_FAMILY_BY_ACCOUNT_ID = null;
    String ADD_EXPENSE_FAMILY_BY_ACCOUNT_ID = null;

    String GET_INCOMES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID = "Select ";
    String GET_EXPENSES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID = null;

    String GET_INCOMES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID = null;
    String GET_EXPENSES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID = null;



    void addIncomePersonalByAccId(BigInteger id, int income);

    void addExpensePersonaByAccId(BigInteger id, int expense);

    void addIncomeFamilyByAccId(BigInteger id, int income);

    void addExpenseFamilyByAccId(BigInteger id, int expense);

    List<AccountIncome> getIncomesPersonalAfterDateByAccountId(BigInteger id, Date date);

    List<AccountExpense> getExpensesPersonalAfterDateByAccountId(BigInteger id, Date date);

    List<AccountIncome> getIncomesFamilyAfterDateByAccountId(BigInteger id, Date data);

    List<AccountExpense> getExpensesFamilyAfterDateByAccountId(BigInteger id, Date data);

}
