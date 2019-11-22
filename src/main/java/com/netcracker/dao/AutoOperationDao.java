package com.netcracker.dao;

import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.models.enums.CategoryIncome;
import com.netcracker.models.enums.CategoryExpense;

import java.math.BigInteger;

public interface AutoOperationDao {
    static final String GET_INCOME_PERSONAL_AO = "SELECT * FROM OBJECTS WHERE OBJECT_ID = ?";
    static final String GET_INCOME_FAMILY_AO = "SELECT * FROM OBJECTS WHERE OBJECT_ID = ?";
    static final String GET_EXPENSE_PERSONAL_AO = "SELECT * FROM OBJECTS WHERE OBJECT_ID = ?";
    static final String GET_EXPENSE_FAMILY_AO = "SELECT * FROM OBJECTS WHERE OBJECT_ID = ?";

    static final String CREATE_INCOME_PERSONAL_AO = "INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, " +
            "DESCRIPTION) VALUES (objects_id_s.nextval, NULL, 12, 'ACC_AUTO_INCOME_PER', 'account auto_income personal1');";
    static final String CREATE_INCOME_FAMILY_AO = "INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, " +
            "DESCRIPTION) VALUES (objects_id_s.nextval, NULL, 23, 'ACC_AUTO_INCOME_FAM', 'account auto_income family1');";
    static final String CREATE_EXPENSE_PERSONAL_AO = "INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, " +
            "DESCRIPTION) VALUES (objects_id_s.nextval, NULL, 11, 'ACC_AUTO_EXPENSE_PER', 'account auto_expense personal1');";
    static final String CREATE_EXPENSE_FAMILY_AO = "INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, " +
            "DESCRIPTION) VALUES (objects_id_s.nextval, NULL, 22, 'ACC_AUTO_EXPENSE_FAM', 'account auto_expense family1');";

    static final String INSERT_INCOME_PERSONAL_AO = "";
    static final String INSERT_INCOME_FAMILY_AO = "";
    static final String INSERT_EXPENSE_PERSONAL_AO = "";
    static final String INSERT_EXPENSE_FAMILY_AO = "";

    static final String DELETE_INCOME_PERSONAL_AO = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";
    static final String DELETE_INCOME_FAMILY_AO = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";
    static final String DELETE_EXPENSE_PERSONAL_AO = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";
    static final String DELETE_EXPENSE_FAMILY_AO = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";

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
