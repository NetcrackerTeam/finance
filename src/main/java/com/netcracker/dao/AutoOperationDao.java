package com.netcracker.dao;

import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.CategoryExpense;
import com.netcracker.models.CategoryIncome;

public interface AutoOperationDao {
    public void createFamilyIncomeAutoOper(CategoryIncome income);

    public void createPersonalIncomeAutoOper(CategoryIncome income );

    public void deleteFamilyIncomeAutoOper( );

    public void deletePersonalIncomeAutoOper(CategoryIncome income);

    public void createFamilyExpenseAutoOper(CategoryExpense expense);

    public void createPersonalExpenseAutoOper(CategoryExpense expense);

    public void deleteFamilyIncExpenseOper();

    public void deletePersonalExpenseAutoOper();

}
