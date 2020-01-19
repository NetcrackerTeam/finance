package com.netcracker.dao;

import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;

import java.math.BigInteger;
import java.util.List;

public interface AutoOperationDao {
    AutoOperationIncome getFamilyIncomeAutoOperation(BigInteger autoOperationId);

    AutoOperationExpense getFamilyExpenseAutoOperation(BigInteger autoOperationId);

    AutoOperationIncome getPersonalIncomeAutoOperation(BigInteger autoOperationId);

    AutoOperationExpense getPersonalExpenseAutoOperation(BigInteger autoOperationId);

    void createFamilyIncomeAutoOperation(AutoOperationIncome autoOperationIncome, BigInteger userId,
                                                        BigInteger familyDebitAccountId);

    void createPersonalIncomeAutoOperation(AutoOperationIncome autoOperationIncome, BigInteger personalDebitAccountId);

    void createFamilyExpenseAutoOperation(AutoOperationExpense autoOperationExpense, BigInteger userId,
                                                          BigInteger familyDebitAccountId);

    void createPersonalExpenseAutoOperation(AutoOperationExpense autoOperationExpense, BigInteger personalDebitAccountId);

    void deleteAutoOperation(BigInteger autoOperationId);

    List<AutoOperationIncome> getAllTodayOperationsPersonalIncome(int dayOfMonth);
    List<AutoOperationExpense> getAllTodayOperationsPersonalExpense(int dayOfMonth);
    List<AutoOperationIncome> getAllTodayOperationsFamilyIncome(int dayOfMonth);
    List<AutoOperationExpense> getAllTodayOperationsFamilyExpense(int dayOfMonth);
    List<AutoOperationExpense> getAllOperationsFamilyExpense(BigInteger debitId);
    List<AutoOperationIncome> getAllOperationsFamilyIncome(BigInteger debitId);
    List<AutoOperationIncome> getAllOperationsPersonalIncome(BigInteger debitId);
    List<AutoOperationExpense> getAllOperationsPersonalExpense(BigInteger debitId);

    int personalIncome_object_type_id_1 = 12;
    String personalIncome_name_2 = "PERSONAL_INCOME_AO";
    int personalExpense_object_type_id_1 = 11;
    String personalExpense_name_2 = "PERSONAL_EXPENSE_AO";
    int familyIncome_object_type_id_1 = 23;
    String familyIncome_name_2 = "FAMILY_INCOME_AO";
    int familyExpense_object_type_id_1 = 22;
    String familyExpense_name_2 = "FAMILY_EXPENSE_AO";

    String CREATE_OBJECT_AUTO_OPERATION = "INSERT INTO OBJECTS (OBJECT_ID, PARENT_ID, OBJECT_TYPE_ID, NAME, DESCRIPTION) " +
            "VALUES (OBJECTS_ID_S.NEXTVAL, NULL, ?, ?, NULL)";

    String CREATE_PERSONAL_INCOME_AO = "INSERT ALL " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (67, ? /*PERSONAL_INCOME*/, ? /*DAY_OF_MONTH*/, NULL, NULL) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (56 /*AMOUNT*/, ?, ? /*AMOUNT*/, NULL, NULL) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (57 /*CATEGORY*/, ?, NULL, NULL, ? /*32-36*/) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (58 /*CURRENT_DATE*/, ?, NULL, SYSDATE, NULL) " +
            "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) VALUES " +
            "(64, ? /*EXPENSE_OBJECT_ID*/, ? /*PERSONAL_DEBIT_ACC_OBJECT_ID*/) " + "SELECT * FROM DUAL";

    String CREATE_PERSONAL_EXPENSE_AO = "INSERT ALL " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (62, ? /*PERSONAL_EXPENSE*/, ? /*DAY_OF_MONTH*/, NULL, NULL) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (50 /*AMOUNT*/, ?, ? /*AMOUNT*/, NULL, NULL) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (51 /*CATEGORY*/, ?, NULL, NULL, ? /*14-18*/) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (52 /*CURRENT_DATE*/, ?, NULL, SYSDATE, NULL) " +
            "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) " +
            "VALUES (59, ? /*EXPENSE_OBJECT_ID*/, ? /*PERSONAL_DEBIT_ACC_OBJECT_ID*/) " + "SELECT * FROM DUAL";

    String CREATE_FAMILY_INCOME_AO = "INSERT ALL " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (68, ? /*FAMILY_INCOME*/, ? /*DAY_OF_MONTH*/, NULL, NULL) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (56 /*AMOUNT*/, ?, ? /*AMOUNT*/, NULL, NULL) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (57 /*CATEGORY*/, ?, NULL, NULL, ? /*32-36*/) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (58 /*CURRENT_DATE*/, ?, NULL, SYSDATE, NULL) " +
            "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) " +
            "VALUES (65, ? /*INCOME_OBJECT_ID*/, ? /*FAMILY_DEBIT_ACC_OBJECT_ID*/) " +
            "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) " +
            "VALUES (66 /*TRANSACTION_AUTO_INCOME*/, ?, ? /*REFERENCE_TO_USER*/) " + "SELECT * FROM DUAL";

    String CREATE_FAMILY_EXPENSE_AO = "INSERT ALL " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (63, ? /*FAMILY_EXPENSE*/, ? /*DAY_OF_MONTH*/, NULL, NULL) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (50 /*AMOUNT*/, ?, ? /*AMOUNT*/, NULL, NULL) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (51 /*CATEGORY*/, ?, NULL, NULL, ? /*14-18*/) " +
            "INTO ATTRIBUTES (ATTR_ID, OBJECT_ID, VALUE, DATE_VALUE, LIST_VALUE_ID) " +
            "VALUES (52 /*CURRENT_DATE*/, ?, NULL, SYSDATE, NULL) " +
            "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) " +
            "VALUES (60, ? /*EXPENSE_OBJECT_ID*/, ? /*FAMILY_DEBIT_ACC_OBJECT_ID*/) " +
            "INTO OBJREFERENCE (ATTR_ID, OBJECT_ID, REFERENCE) " +
            "VALUES (61 /*TRANSACTION_AUTO_EXPENSE*/, ?, ? /*REFERENCE_TO_USER*/) " + "SELECT * FROM DUAL";

    String DELETE_FROM_OBJECTS = "DELETE FROM OBJECTS WHERE OBJECT_ID = ?";

    int personalExpense_user_debit_acc_ref_attr_id_1 = 59; //PERSONAL_DEBET_ACCOUNT_ID
    int personalExpense_day_of_month_attr_id_3 = 62; //DAY_OF_MONTH
    int personalIncome_user_debit_acc_ref_attr_id_1 = 64; //PERSONAL_DEBET_ACCOUNT_ID
    int personalIncome_day_of_month_attr_id_3 = 67; //DAY_OF_MONTH

    int familyExpense_family_ref_attr_id_1 = 60; //FAMILY_DEBET_ACCOUNT_ID
    int familyExpense_users_ref_attr_id_2 = 61; //TRANSACTION_AUTOEXPENSE_USER_ID
    int familyExpense_day_of_month_attr_id_4 = 63; //DAY_OF_MONTH
    int familyIncome_family_ref_attr_id_1 = 65; //FAMILY_DEBET_ACCOUNT_ID
    int familyIncome_users_ref_attr_id_2 = 66; //TRANSACTION_AUTOEXPENSE_USER_ID
    int familyIncome_day_of_month_attr_id_4 = 68; //DAY_OF_MONTH

    int income_amount_attr_id_5 = 56; //AMOUNT
    int income_category_attr_id_6 = 57; //CATEGORY
    int income_dates_attr_id_7 = 58; //DATE
    int expense_amount_attr_id_5 = 50; //AMOUNT
    int expense_category_attr_id_6 = 51; //CATEGORY
    int expense_dates_attr_id_7 = 52; //DATE

    String GET_PERSONAL_AO = "SELECT AUTO_OPERATION.OBJECT_ID AS AO_OBJECT_ID, AUTO_OPERATION.NAME, USER_DEBIT_ACC_REF.REFERENCE AS DEBIT_ID, " +
            "              DAY_OF_MONTH.VALUE AS DAY_OF_MONTH, AMOUNT.VALUE AS AMOUNT, " +
            "              CATEGORY.LIST_VALUE_ID AS CATEGORY_ID, LISTS.VALUE AS CATEGORY, DATES.DATE_VALUE AS DATE_OF_CREATION " +
            "            FROM OBJECTS AUTO_OPERATION, LISTS, OBJREFERENCE USER_DEBIT_ACC_REF, " +
            "              ATTRIBUTES AMOUNT, " +
            "              ATTRIBUTES CATEGORY, ATTRIBUTES DATES, ATTRIBUTES DAY_OF_MONTH " +
            "            WHERE USER_DEBIT_ACC_REF.ATTR_ID = ?" +
            "                      AND AUTO_OPERATION.OBJECT_ID = ? " +
            "                      AND AUTO_OPERATION.OBJECT_ID = USER_DEBIT_ACC_REF.OBJECT_ID " +
            "                      AND DAY_OF_MONTH.ATTR_ID = ? " +
            "                      AND AMOUNT.ATTR_ID = ? " +
            "                      AND CATEGORY.ATTR_ID = ? " +
            "                      AND DATES.ATTR_ID = ? " +
            "                      AND CATEGORY.LIST_VALUE_ID = LISTS.LIST_VALUE_ID " +
            "                      AND DAY_OF_MONTH.OBJECT_ID = AUTO_OPERATION.OBJECT_ID " +
            "                      AND AMOUNT.OBJECT_ID = AUTO_OPERATION.OBJECT_ID " +
            "                      AND CATEGORY.OBJECT_ID = AUTO_OPERATION.OBJECT_ID " +
            "                      AND DATES.OBJECT_ID = AUTO_OPERATION.OBJECT_ID";

    String GET_FAMILY_AO = "SELECT AUTO_OPERATION.OBJECT_ID AS AO_OBJECT_ID, AUTO_OPERATION.NAME, " +
            "       FAMILY_REF.REFERENCE AS DEBIT_ID, " +
            "       USERS_REF.REFERENCE AS USER_ID, " +
            "       DAY_OF_MONTH.VALUE AS DAY_OF_MONTH, AMOUNT.VALUE AS AMOUNT, " +
            "       CATEGORY.LIST_VALUE_ID AS CATEGORY_ID, LISTS.VALUE AS CATEGORY, DATES.DATE_VALUE AS DATE_OF_CREATION " +
            "FROM OBJECTS AUTO_OPERATION, LISTS, " +
            "     ATTRIBUTES AMOUNT, ATTRIBUTES CATEGORY, ATTRIBUTES DATES, ATTRIBUTES DAY_OF_MONTH, OBJREFERENCE FAMILY_REF, OBJREFERENCE USERS_REF " +
            "WHERE FAMILY_REF.ATTR_ID = ? " +
            "  AND USERS_REF.ATTR_ID = ? " +
            "  AND AUTO_OPERATION.OBJECT_ID = ? " +
            "  AND AUTO_OPERATION.OBJECT_ID = FAMILY_REF.OBJECT_ID " +
            "  AND AUTO_OPERATION.OBJECT_ID = USERS_REF.OBJECT_ID " +
            "  AND DAY_OF_MONTH.ATTR_ID = ? " +
            "  AND AMOUNT.ATTR_ID = ? " +
            "  AND CATEGORY.ATTR_ID = ? " +
            "  AND DATES.ATTR_ID = ? " +
            "  AND CATEGORY.LIST_VALUE_ID = LISTS.LIST_VALUE_ID " +
            "  AND DAY_OF_MONTH.OBJECT_ID = AUTO_OPERATION.OBJECT_ID " +
            "  AND AMOUNT.OBJECT_ID = AUTO_OPERATION.OBJECT_ID " +
            "  AND CATEGORY.OBJECT_ID = AUTO_OPERATION.OBJECT_ID " +
            "  AND DATES.OBJECT_ID = AUTO_OPERATION.OBJECT_ID";

    String GET_ALL_TODAY_AO_INCOME_PERSONAL = "SELECT DEBIT_REF.OBJECT_ID AS AO_OBJECT_ID, DEBIT_REF.REFERENCE AS DEBIT_ID, " +
            "       DAY_OF_MONTH.VALUE AS DAY_OF_MONTH, AMOUNT.VALUE AS AMOUNT, CATEGORY.LIST_VALUE_ID AS CATEGORY_ID, " +
            "       LISTS.VALUE AS CATEGORY, DATES.DATE_VALUE AS DATE_OF_CREATION " +
            "FROM LISTS, ATTRIBUTES AMOUNT, ATTRIBUTES CATEGORY, ATTRIBUTES DATES, " +
            "     OBJREFERENCE DEBIT_REF, ATTRIBUTES DAY_OF_MONTH " +
            "WHERE DAY_OF_MONTH.ATTR_ID = 67 /*DAY_OF_MONTH*/ " +
            "  AND DAY_OF_MONTH.VALUE = ? " +
            "  AND AMOUNT.ATTR_ID = 56 /*AMOUNT*/ " +
            "  AND CATEGORY.LIST_VALUE_ID = LISTS.LIST_VALUE_ID " +
            "  AND CATEGORY.ATTR_ID = 57 /*CATEGORY*/ " +
            "  AND DATES.ATTR_ID = 58 /*DATE*/ " +
            "  AND DEBIT_REF.ATTR_ID = 64 /*PERSONAL_DEBET_ACCOUNT_ID*/ " +
            "  AND DAY_OF_MONTH.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "  AND AMOUNT.OBJECT_ID = DEBIT_REF.OBJECT_ID " +
            "  AND CATEGORY.OBJECT_ID = DEBIT_REF.OBJECT_ID " +
            "  AND DATES.OBJECT_ID = DEBIT_REF.OBJECT_ID ORDER BY AO_OBJECT_ID";

    String GET_ALL_TODAY_AO_INCOME_FAMILY = "SELECT DEBIT_REF.OBJECT_ID AS AO_OBJECT_ID, DEBIT_REF.REFERENCE AS DEBIT_ID, USER_REF.REFERENCE AS USER_ID, " +
            "       DAY_OF_MONTH.VALUE AS DAY_OF_MONTH, AMOUNT.VALUE AS AMOUNT, CATEGORY.LIST_VALUE_ID AS CATEGORY_ID, " +
            "       LISTS.VALUE AS CATEGORY, DATES.DATE_VALUE AS DATE_OF_CREATION " +
            "FROM LISTS, ATTRIBUTES AMOUNT, ATTRIBUTES CATEGORY, ATTRIBUTES DATES, " +
            "     OBJREFERENCE DEBIT_REF, ATTRIBUTES DAY_OF_MONTH, OBJREFERENCE USER_REF " +
            "WHERE DAY_OF_MONTH.ATTR_ID = 68 /*DAY_OF_MONTH*/ " +
            "  AND DAY_OF_MONTH.VALUE = ? " +
            "  AND USER_REF.ATTR_ID = 66 /*TRANSACTION_AUTOINCOME_USER_ID*/ " +
            "  AND DEBIT_REF.OBJECT_ID = USER_REF.OBJECT_ID " +
            "  AND AMOUNT.ATTR_ID = 56 /*AMOUNT*/ " +
            "  AND CATEGORY.LIST_VALUE_ID = LISTS.LIST_VALUE_ID " +
            "  AND CATEGORY.ATTR_ID = 57 /*CATEGORY*/ " +
            "  AND DATES.ATTR_ID = 58 /*DATE*/ " +
            "  AND DEBIT_REF.ATTR_ID = 65 /*FAMILY_DEBET_ACCOUNT_ID*/ " +
            "  AND DAY_OF_MONTH.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "  AND AMOUNT.OBJECT_ID = DEBIT_REF.OBJECT_ID " +
            "  AND CATEGORY.OBJECT_ID = DEBIT_REF.OBJECT_ID " +
            "  AND DATES.OBJECT_ID = DEBIT_REF.OBJECT_ID ORDER BY AO_OBJECT_ID";

    String GET_ALL_TODAY_AO_EXPENSE_PERSONAL = "SELECT DEBIT_REF.OBJECT_ID AS AO_OBJECT_ID, DEBIT_REF.REFERENCE AS DEBIT_ID, " +
            "       DAY_OF_MONTH.VALUE AS DAY_OF_MONTH, AMOUNT.VALUE AS AMOUNT, CATEGORY.LIST_VALUE_ID AS CATEGORY_ID, " +
            "       LISTS.VALUE AS CATEGORY, DATES.DATE_VALUE AS DATE_OF_CREATION " +
            "FROM LISTS, ATTRIBUTES AMOUNT, ATTRIBUTES CATEGORY, ATTRIBUTES DATES, " +
            "     OBJREFERENCE DEBIT_REF, ATTRIBUTES DAY_OF_MONTH " +
            "WHERE DAY_OF_MONTH.ATTR_ID = 62 /*DAY_OF_MONTH*/ " +
            "  AND DAY_OF_MONTH.VALUE = ? " +
            "  AND AMOUNT.ATTR_ID = 50 /*AMOUNT*/ " +
            "  AND CATEGORY.LIST_VALUE_ID = LISTS.LIST_VALUE_ID " +
            "  AND CATEGORY.ATTR_ID = 51 /*CATEGORY*/ " +
            "  AND DATES.ATTR_ID = 52 /*DATE*/ " +
            "  AND DEBIT_REF.ATTR_ID = 59 /*PERSONAL_DEBET_ACCOUNT_ID*/ " +
            "  AND DAY_OF_MONTH.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "  AND AMOUNT.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "  AND CATEGORY.OBJECT_ID = DEBIT_REF.OBJECT_ID " +
            "  AND DATES.OBJECT_ID = DEBIT_REF.OBJECT_ID  ORDER BY AO_OBJECT_ID";

    String GET_ALL_TODAY_AO_EXPENSE_FAMILY = "SELECT DEBIT_REF.OBJECT_ID  AS AO_OBJECT_ID, DEBIT_REF.REFERENCE AS DEBIT_ID, USER_REF.REFERENCE AS USER_ID, " +
            "       DAY_OF_MONTH.VALUE AS DAY_OF_MONTH, AMOUNT.VALUE AS AMOUNT, CATEGORY.LIST_VALUE_ID AS CATEGORY_ID, " +
            "       LISTS.VALUE AS CATEGORY, DATES.DATE_VALUE AS DATE_OF_CREATION " +
            "FROM LISTS, ATTRIBUTES AMOUNT, ATTRIBUTES CATEGORY, ATTRIBUTES DATES, " +
            "     OBJREFERENCE DEBIT_REF, ATTRIBUTES DAY_OF_MONTH, OBJREFERENCE USER_REF " +
            "WHERE DAY_OF_MONTH.ATTR_ID = 63 /*DAY_OF_MONTH*/ " +
            "  AND DAY_OF_MONTH.VALUE = ? " +
            "  AND USER_REF.ATTR_ID = 61 /*TRANSACTION_AUTOINCOME_USER_ID*/ " +
            "  AND DEBIT_REF.OBJECT_ID = USER_REF.OBJECT_ID " +
            "  AND AMOUNT.ATTR_ID = 50 /*AMOUNT*/ " +
            "  AND CATEGORY.LIST_VALUE_ID = LISTS.LIST_VALUE_ID " +
            "  AND CATEGORY.ATTR_ID = 51 /*CATEGORY*/ " +
            "  AND DATES.ATTR_ID = 52 /*DATE*/ " +
            "  AND DEBIT_REF.ATTR_ID = 60 /*FAMILY_DEBET_ACCOUNT_ID*/ " +
            "  AND DAY_OF_MONTH.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "  AND AMOUNT.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "  AND CATEGORY.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "  AND DATES.OBJECT_ID = DEBIT_REF.OBJECT_ID ORDER BY AO_OBJECT_ID";

    String GET_ALL_AO_INCOME_PERSONAL = "SELECT DEBIT_REF.OBJECT_ID AS AO_OBJECT_ID, DEBIT_REF.REFERENCE AS DEBIT_ID, " +
            "                       DAY_OF_MONTH.VALUE AS DAY_OF_MONTH, AMOUNT.VALUE AS AMOUNT, CATEGORY.LIST_VALUE_ID AS CATEGORY_ID, " +
            "                       LISTS.VALUE AS CATEGORY, DATES.DATE_VALUE AS DATE_OF_CREATION " +
            "                    FROM LISTS, ATTRIBUTES AMOUNT, ATTRIBUTES CATEGORY, ATTRIBUTES DATES, " +
            "                       OBJREFERENCE DEBIT_REF, ATTRIBUTES DAY_OF_MONTH " +
            "                    WHERE DAY_OF_MONTH.ATTR_ID = 67 /*DAY_OF_MONTH*/ " +
            "                       AND DEBIT_REF.REFERENCE = ? " +
            "                      AND AMOUNT.ATTR_ID = 56 /*AMOUNT*/ " +
            "                       AND CATEGORY.LIST_VALUE_ID = LISTS.LIST_VALUE_ID " +
            "                       AND CATEGORY.ATTR_ID = 57 /*CATEGORY*/ " +
            "                       AND DATES.ATTR_ID = 58 /*DATE*/ " +
            "                       AND DEBIT_REF.ATTR_ID = 64 /*PERSONAL_DEBET_ACCOUNT_ID*/ " +
            "                       AND DAY_OF_MONTH.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "                       AND AMOUNT.OBJECT_ID = DEBIT_REF.OBJECT_ID " +
            "                       AND CATEGORY.OBJECT_ID = DEBIT_REF.OBJECT_ID " +
            "                       AND DATES.OBJECT_ID = DEBIT_REF.OBJECT_ID ORDER BY AO_OBJECT_ID";

    String GET_ALL_AO_INCOME_FAMILY = "SELECT DEBIT_REF.OBJECT_ID AS AO_OBJECT_ID, DEBIT_REF.REFERENCE AS DEBIT_ID, USER_REF.REFERENCE AS USER_ID, " +
            "                       DAY_OF_MONTH.VALUE AS DAY_OF_MONTH, AMOUNT.VALUE AS AMOUNT, CATEGORY.LIST_VALUE_ID AS CATEGORY_ID, " +
            "                       LISTS.VALUE AS CATEGORY, DATES.DATE_VALUE AS DATE_OF_CREATION " +
            "                    FROM LISTS, ATTRIBUTES AMOUNT, ATTRIBUTES CATEGORY, ATTRIBUTES DATES, " +
            "                       OBJREFERENCE DEBIT_REF, ATTRIBUTES DAY_OF_MONTH, OBJREFERENCE USER_REF " +
            "                    WHERE DAY_OF_MONTH.ATTR_ID = 68 /*DAY_OF_MONTH*/ " +
            "                       AND DEBIT_REF.REFERENCE = ? " +
            "                       AND USER_REF.ATTR_ID = 66 /*TRANSACTION_AUTOINCOME_USER_ID*/ " +
            "                       AND DEBIT_REF.OBJECT_ID = USER_REF.OBJECT_ID " +
            "                       AND AMOUNT.ATTR_ID = 56 /*AMOUNT*/ " +
            "                       AND CATEGORY.LIST_VALUE_ID = LISTS.LIST_VALUE_ID " +
            "                       AND CATEGORY.ATTR_ID = 57 /*CATEGORY*/ " +
            "                       AND DATES.ATTR_ID = 58 /*DATE*/ " +
            "                       AND DEBIT_REF.ATTR_ID = 65 /*FAMILY_DEBET_ACCOUNT_ID*/ " +
            "                       AND DAY_OF_MONTH.OBJECT_ID = DEBIT_REF.OBJECT_ID " +
            "                       AND AMOUNT.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "                       AND CATEGORY.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "                       AND DATES.OBJECT_ID = DEBIT_REF.OBJECT_ID  ORDER BY AO_OBJECT_ID";

    String GET_ALL_AO_EXPENSE_PERSONAL = "SELECT DEBIT_REF.OBJECT_ID  AS AO_OBJECT_ID, DEBIT_REF.REFERENCE AS DEBIT_ID, " +
            "                       DAY_OF_MONTH.VALUE AS DAY_OF_MONTH, AMOUNT.VALUE AS AMOUNT, CATEGORY.LIST_VALUE_ID AS CATEGORY_ID, " +
            "                       LISTS.VALUE AS CATEGORY, DATES.DATE_VALUE AS DATE_OF_CREATION " +
            "                    FROM LISTS, ATTRIBUTES AMOUNT, ATTRIBUTES CATEGORY, ATTRIBUTES DATES, " +
            "                    OBJREFERENCE DEBIT_REF, ATTRIBUTES DAY_OF_MONTH " +
            "                    WHERE DAY_OF_MONTH.ATTR_ID = 62 /*DAY_OF_MONTH*/ " +
            "                       AND DEBIT_REF.REFERENCE = ? " +
            "                       AND AMOUNT.ATTR_ID = 50 /*AMOUNT*/ " +
            "                       AND CATEGORY.LIST_VALUE_ID = LISTS.LIST_VALUE_ID " +
            "                       AND CATEGORY.ATTR_ID = 51 /*CATEGORY*/ " +
            "                       AND DATES.ATTR_ID = 52 /*DATE*/ " +
            "                       AND DEBIT_REF.ATTR_ID = 59 /*PERSONAL_DEBET_ACCOUNT_ID*/ " +
            "                       AND DAY_OF_MONTH.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "                       AND AMOUNT.OBJECT_ID = DEBIT_REF.OBJECT_ID " +
            "                       AND CATEGORY.OBJECT_ID = DEBIT_REF.OBJECT_ID " +
            "                       AND DATES.OBJECT_ID = DEBIT_REF.OBJECT_ID  ORDER BY AO_OBJECT_ID";

    String GET_ALL_AO_EXPENSE_FAMILY = "SELECT DEBIT_REF.OBJECT_ID  AS AO_OBJECT_ID, DEBIT_REF.REFERENCE AS DEBIT_ID, USER_REF.REFERENCE AS USER_ID, " +
            "       DAY_OF_MONTH.VALUE AS DAY_OF_MONTH, AMOUNT.VALUE AS AMOUNT, CATEGORY.LIST_VALUE_ID AS CATEGORY_ID, " +
            "       LISTS.VALUE AS CATEGORY, DATES.DATE_VALUE AS DATE_OF_CREATION " +
            "FROM  LISTS, ATTRIBUTES AMOUNT, ATTRIBUTES CATEGORY, ATTRIBUTES DATES, " +
            "     OBJREFERENCE DEBIT_REF, ATTRIBUTES DAY_OF_MONTH, OBJREFERENCE USER_REF " +
            "WHERE DAY_OF_MONTH.ATTR_ID = 63 /*DAY_OF_MONTH*/ " +
            "  AND DEBIT_REF.REFERENCE = ? " +
            "  AND USER_REF.ATTR_ID = 61 /*TRANSACTION_AUTOINCOME_USER_ID*/ " +
            "  AND DEBIT_REF.OBJECT_ID = USER_REF.OBJECT_ID " +
            "    AND AMOUNT.ATTR_ID = 50 /*AMOUNT*/ " +
            "    AND CATEGORY.LIST_VALUE_ID = LISTS.LIST_VALUE_ID " +
            "    AND CATEGORY.ATTR_ID = 51 /*CATEGORY*/ " +
            "    AND DATES.ATTR_ID = 52 /*DATE*/ " +
            "    AND DEBIT_REF.ATTR_ID = 60 /*FAMILY_DEBET_ACCOUNT_ID*/ " +
            "  AND DAY_OF_MONTH.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "  AND AMOUNT.OBJECT_ID = DEBIT_REF.OBJECT_ID  " +
            "  AND CATEGORY.OBJECT_ID = DEBIT_REF.OBJECT_ID " +
            "  AND DATES.OBJECT_ID = DEBIT_REF.OBJECT_ID ORDER BY AO_OBJECT_ID";
}
