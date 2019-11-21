package com.netcracker.dao;

import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.CategoryExpense;
import com.netcracker.models.CategoryIncome;

import java.math.BigInteger;

public interface AutoOperationDao {
    static final String CREATE_FAMILY_EXPENSE_AO = "INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME," +
            "DESCRIPTION) VALUES (objects_id_s.nextval, NULL, 22, ?, 'account auto_expense personal family');";
    static final String CREATE_PERSONAL_EXPENSE_AO = "INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME," +
            "DESCRIPTION) VALUES (objects_id_s.nextval, NULL, 11, ?, 'account auto_expense personal');";
    static final String CREAGTE_PERSONAL_INCOME_AO = " INSERT INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME," +
            "DESCRIPTION) VALUES (objects_id_s.nextval, NULL, 12, ?,'account auto_income personal');";
    static final String CREATE_FAMILY_INCOME_AO = "INSERT INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME," +
            "DESCRIPTION) VALUES (objects_id_s.nextval, NULL, 23, ?','account auto_income personal family');";
    static final String DELETE_FAMILY_INCOME_AO = "";
    static final String DELETE_FAMILY_EXPENSE_AO = "";
    static final String DELETE_PERSONAL_INCOME_AO = "";
    static final String DELETE_PERSONAL_EXPENSE_AO = "";
    static final String GET_INCOME_AO = "SELECT * FROM OBJECTS WHERE OBJECT_ID = ?";
    static final String GET_EXPENS_AO = "SELECT * FROM OBJECTS WHERE OBJECT_ID = ?";

    AutoOperationIncome getAutoOperationIncomeById(BigInteger autoOperationId);
    AutoOperationExpense getAutoOperationExpenseById(BigInteger autoOperationId);

    void createFamilyIncomeAutoOperation(CategoryIncome income);

    void createPersonalIncomeAutoOperation(CategoryIncome income );

    void deleteFamilyIncomeAutoOperation(BigInteger autoOperationId);

    void deletePersonalIncomeAutoOperation(CategoryIncome income);

    void createFamilyExpenseAutoOperation(CategoryExpense expense);

    void createPersonalExpenseAutoOperation(CategoryExpense expense);

    void deleteFamilyIncExpenseOperation(BigInteger autoOperationId);

    void deletePersonalExpenseAutoOperation(BigInteger autoOperationId);
}
