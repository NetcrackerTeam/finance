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

    String GET_PERSONAL_INCOME_OPERATIONS_BY_ID_DATE = "Select ";
    String GET_PERSONAL_EXPENSE_OPERATIONS_BY_ID_DATE = null;

    String GET_FAMILY_INCOME_OPERATIONS_BY_ID_DATE = null;
    String GET_FAMILY_EXPENSE_OPERATIONS_BY_ID_DATE = null;

    String GET_CATEGORIES_INCOME = null;
    String GET_CATEGORIES_EXPENSE = null;


    void addIncomePersonalByAccId(BigInteger id, int income);

    void addExpensePersonaByAccId(BigInteger id, int expense);

    void addIncomeFamilyByAccId(BigInteger id, int income);

    void addExpenseFamilyByAccId(BigInteger id, int expense);

    List<AccountIncome> getPersonalIncomeOperationsByIdDate(BigInteger id, Date date);

    List<AccountExpense> getPersonalExpenseOperationsByIdDate(BigInteger id, Date date);

    List<AccountIncome> getFamilyIncomeOperationsByIdDate(BigInteger id, Date data);

    List<AccountExpense> getFamilyExpenseOperationsByIdDate(BigInteger id, Date data);

    Map<CategoryIncome, Long> getCategoriesIncome();

    Map<CategoryExpense, Long> getCategoriesExpense();
}
