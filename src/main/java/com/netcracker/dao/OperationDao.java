package com.netcracker.dao;

import com.netcracker.models.*;

import java.math.BigInteger;
import java.util.Date;

public interface OperationDao {
    public void addIncomePersonalByAccId(BigInteger id, int income);

    public void addExpencePersonaByAccId(BigInteger id, int expense);

    public void addIncomeFamilyByAccId(BigInteger id, int income);

    public void addExpenseFamilyByAccId(BigInteger id, int expense);

    public void getPersonalIncomeOperationsByIdDate(BigInteger id, Date date);

    public void getPersonalExpenseOperationsByIdDate(BigInteger id, Date date);

    public void getFamilyIncomeOperationsByIdDate(BigInteger id, Date data);

    public void getFamilyExpenseOperationsByIdDate(BigInteger id, Date data);

    public CategoryIncome getCategoriesIncome();

    public CategoryExpense getCategoriesExpense();
}
