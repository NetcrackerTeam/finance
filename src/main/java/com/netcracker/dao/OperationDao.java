package com.netcracker.dao;

import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigInteger;
import java.util.Date;

public interface OperationDao {

    String ADD_INCOME_PERSONAL_BY_ACCOUNT_ID = "INSERT INTO objects(OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) " +
            "VALUES(objects_id_s.nextval, NULL, , '' , '');" +
            "INSERT INTO objreference(ATTR_ID,OBJECT_ID,REFERENCE) VALUES (?, ? ,?) ";
    String ADD_EXPENSE_PERSONAL_BY_ACCOUNT_ID = "INSERT INTO objects(OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) " +
            "VALUES(objects_id_s.nextval, NULL, , ? , ?);" +
            "INSERT INTO objreference(ATTR_ID,OBJECT_ID,REFERENCE) VALUES (?, ? ,?); ";

    String ADD_INCOME_FAMILY_BY_ACCOUNT_ID = "INSERT INTO objects(OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) " +
            "VALUES(objects_id_s.nextval, NULL, , ? , ?);" +
            "INSERT INTO objreference(ATTR_ID,OBJECT_ID,REFERENCE) VALUES (?, ? ,?); ";
    String ADD_EXPENSE_FAMILY_BY_ACCOUNT_ID = "INSERT INTO objects(OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) " +
            "VALUES(objects_id_s.nextval, NULL, , ? , ?);" +
            "INSERT INTO objreference(ATTR_ID,OBJECT_ID,REFERENCE) VALUES (?, ? ,?); ";

    String GET_PERSONAL_INCOME_OPERATION_BY_ID_DATE = "Select ";
    String GET_PERSONAL_EXPENSE_OPERATION_BY_ID_DATE = null;

    String GET_FAMILY_INCOME_OPERATION_BY_ID_DATE = null;
    String GET_FAMILY_EXPENSE_OPERATION_BY_ID_DATE = null;

    String GET_CATEGORIES_INCOME = null;
    String GET_CATEGORIES_EXPENSE = null;


    void addIncomePersonalByAccId(BigInteger id, int income);

    void addExpensePersonaByAccId(BigInteger id, int expense);

    void addIncomeFamilyByAccId(BigInteger id, int income);

    void addExpenseFamilyByAccId(BigInteger id, int expense);

    void getPersonalIncomeOperationsByIdDate(BigInteger id, Date date);

    void getPersonalExpenseOperationsByIdDate(BigInteger id, Date date);

    void getFamilyIncomeOperationsByIdDate(BigInteger id, Date data);

    void getFamilyExpenseOperationsByIdDate(BigInteger id, Date data);

    CategoryIncome getCategoriesIncome();

    CategoryExpense getCategoriesExpense();
}
