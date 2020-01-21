package com.netcracker.dao;

import com.netcracker.models.*;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface OperationDao {

    void createIncomePersonalByAccId(BigInteger id, double income, LocalDateTime date, CategoryIncome categoryIncome);

    void createExpensePersonaByAccId(BigInteger id, double expense, LocalDateTime date, CategoryExpense categoryExpense);

    void createIncomeFamilyByAccId(BigInteger idUser, BigInteger idFamily, double income, LocalDateTime date, CategoryIncome categoryIncome);

    void createExpenseFamilyByAccId(BigInteger idUser, BigInteger idFamily, double expense, LocalDateTime date, CategoryExpense categoryExpense);

    List<AccountIncome> getIncomesPersonalAfterDateByAccountId(BigInteger id, LocalDateTime date);

    List<AccountExpense> getExpensesPersonalAfterDateByAccountId(BigInteger id, LocalDateTime date);

    List<AccountIncome> getIncomesFamilyAfterDateByAccountId(BigInteger id, LocalDateTime data);

    List<AccountExpense> getExpensesFamilyAfterDateByAccountId(BigInteger id, LocalDateTime data);

    Collection<CategoryExpenseReport> getExpensesPersonalGroupByCategories(BigInteger id, LocalDateTime date);

    Collection<CategoryIncomeReport> getIncomesPersonalGroupByCategories(BigInteger id, LocalDateTime date);

    Collection<CategoryExpenseReport> getExpensesFamilyGroupByCategories(BigInteger id, LocalDateTime date);

    Collection<CategoryIncomeReport> getIncomesFamilyGroupByCategories(BigInteger id, LocalDateTime date);

    List<HistoryOperation> getHistoryByAccountId(BigInteger id, int period);

    List<HistoryOperation> getFamilyHistoryByAccountId(BigInteger id, int period);

    List<HistoryOperation> getFirstPersonalHistoryByAccountId(BigInteger id);

    List<HistoryOperation> getFirstFamilyHistoryByAccountId(BigInteger id);


    String ADD_INCOME_PERSONAL_BY_ACCOUNT_ID = "INSERT ALL" +
            " INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,10,'ACC_INC_PER1','account income personal1')" +
            " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (53,OBJECTS_ID_S.currval,?)" +
            " INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(56, OBJECTS_ID_S.currval, ?)" +
            " INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(58, OBJECTS_ID_S.currval, ?)" +
            " INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(57, OBJECTS_ID_S.currval, ?)" +
            " SELECT * FROM DUAL";

    String ADD_EXPENSE_PERSONAL_BY_ACCOUNT_ID = "INSERT ALL" +
            " INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (OBJECTS_ID_S.nextval,NULL,9,'account_expense_personal','account_expense_personal')" +
            " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (47,OBJECTS_ID_S.currval,?) /* id */" +
            " INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(50, OBJECTS_ID_S.currval, ?) /* amount */" +
            " INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(52, OBJECTS_ID_S.currval, ?) /* date */" +
            " INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(51, OBJECTS_ID_S.currval, ?) /* category */" +
            " SELECT * FROM DUAL";

    String ADD_INCOME_FAMILY_BY_ACCOUNT_ID = "INSERT ALL" +
            " INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (OBJECTS_ID_S.NEXTVAL,NULL,21,'ACC_FAM_IN','ACCOUNT INCOME FAMILY')" +
            " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (54,OBJECTS_ID_S.CURRVAL,?) /* user */" +
            " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (55,OBJECTS_ID_S.CURRVAL,?) /* family*/" +
            " INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(56, OBJECTS_ID_S.CURRVAL, ?)/* amount */" +
            " INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(58, OBJECTS_ID_S.CURRVAL, ?)/* date */" +
            " INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(57, OBJECTS_ID_S.CURRVAL, ?)/* category */" +
            " SELECT * FROM DUAL";

    String ADD_EXPENSE_FAMILY_BY_ACCOUNT_ID = "INSERT ALL" +
            " INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (OBJECTS_ID_S.nextval,NULL,20,'account_expense_family','account expense Family1')" +
            " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (49, OBJECTS_ID_S.currval,?) /* reference to user*/" +
            " INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (48,OBJECTS_ID_S.currval,?) /* reference to family*/" +
            " INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(50, OBJECTS_ID_S.currval, ?) /* amount */" +
            " INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(52, OBJECTS_ID_S.currval, ?) /* date */" +
            " INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(51, OBJECTS_ID_S.currval, ?) /* category */" +
            " SELECT * FROM DUAL";

    String GET_INCOMES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID = "SELECT O.OBJECT_ID AS ACCOUNT_INCOME_ID,  SUMM.VALUE AS INCOME_AMOUNT," +
            " DATE_OF.DATE_VALUE AS DATE_INCOME, CATEGORY.LIST_VALUE_ID AS CATEGORY_INCOME" +
            " FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF" +
            " WHERE O.OBJECT_TYPE_ID =  10 AND REF.ATTR_ID = 53 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ?" +
            " AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 56 /* amount*/" +
            " AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 57 /* category*/" +
            " AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 58 /* date*/" +
            " AND DATE_OF.DATE_VALUE > ?" +
            " ORDER BY O.OBJECT_ID ";

    String GET_EXPENSES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID = "SELECT O.OBJECT_ID AS ACCOUNT_EXPENSE_ID,  " +
            "SUMM.VALUE AS EXPENSE_AMOUNT, DATE_OF.DATE_VALUE AS DATE_EXPENSE, CATEGORY.LIST_VALUE_ID AS CATEGORY_EXPENSE" +
            " FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF" +
            " WHERE O.OBJECT_TYPE_ID = 9 AND REF.ATTR_ID = 47 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ?" +
            " AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 50 /* amount*/" +
            " AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 51 /* category*/" +
            " AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 52 /* date*/" +
            " AND DATE_OF.DATE_VALUE > ?" +
            " ORDER BY O.OBJECT_ID ";

    String GET_INCOMES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID = "SELECT O.OBJECT_ID AS ACCOUNT_INCOME_ID,  SUMM.VALUE AS INCOME_AMOUNT, " +
            "DATE_OF.DATE_VALUE AS DATE_INCOME, CATEGORY.LIST_VALUE_ID AS CATEGORY_INCOME" +
            " FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF" +
            " WHERE O.OBJECT_TYPE_ID =  21 AND REF.ATTR_ID = 55 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ?" +
            " AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 56 /* amount*/" +
            " AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 57 /* category*/" +
            " AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 58 /* date*/" +
            " AND DATE_OF.DATE_VALUE > ?" +
            " ORDER BY O.OBJECT_ID ";

    String GET_EXPENSES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID = "SELECT O.OBJECT_ID AS ACCOUNT_EXPENSE_ID,  SUMM.VALUE AS EXPENSE_AMOUNT," +
            " DATE_OF.DATE_VALUE AS DATE_EXPENSE, CATEGORY.LIST_VALUE_ID AS CATEGORY_EXPENSE" +
            " FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF" +
            " WHERE O.OBJECT_TYPE_ID =  20 AND REF.ATTR_ID = 48 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ?" +
            " AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 50 /* amount*/" +
            " AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 51 /* category*/" +
            " AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 52/* date*/" +
            " AND DATE_OF.DATE_VALUE > ?" +
            " ORDER BY O.OBJECT_ID ";

    String GET_EXPENSES_PERSONAL_GROUP_BY_CATEGORIES = "SELECT  SUM(SUMM.VALUE) AS AMOUNT,  CATEGORY.LIST_VALUE_ID AS CATEGORY" +
            " FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF" +
            " WHERE O.OBJECT_TYPE_ID = 9 AND REF.ATTR_ID = 47 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ?" +
            " AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 50 /* amount*/" +
            " AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 51 /* category*/" +
            " AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 52 /* date*/" +
            " AND DATE_OF.DATE_VALUE > ?" +
            " GROUP BY CATEGORY.LIST_VALUE_ID";

    String GET_INCOMES_PERSONAL_GROUP_BY_CATEGORIES = "SELECT SUM(SUMM.VALUE) AS AMOUNT, CATEGORY.LIST_VALUE_ID AS CATEGORY" +
            "  FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF" +
            "  WHERE O.OBJECT_TYPE_ID =  10 AND REF.ATTR_ID = 53 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ?" +
            "  AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 56 /* amount*/" +
            "  AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 57 /* category*/" +
            "  AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 58 /* date*/" +
            "  AND DATE_OF.DATE_VALUE > ?" +
            "  GROUP BY CATEGORY.LIST_VALUE_ID";

    String GET_EXPENSES_FAMILY_GROUP_BY_CATEGORIES = "SELECT  SUM(SUMM.VALUE) AS AMOUNT,  CATEGORY.LIST_VALUE_ID AS CATEGORY, CONSUMER.REFERENCE AS USER_ID " +
            "  FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF, OBJREFERENCE CONSUMER " +
            "  WHERE O.OBJECT_TYPE_ID = 20 AND REF.ATTR_ID = 48 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ? " +
            "  AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 50 /* amount*/ " +
            "  AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 51 /* category*/ " +
            "  AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 52 /* date*/ " +
            "  AND CONSUMER.ATTR_ID = 49 AND CONSUMER.OBJECT_ID = O.OBJECT_ID " +
            "  AND DATE_OF.DATE_VALUE > ? " +
            "  GROUP BY CATEGORY.LIST_VALUE_ID, CONSUMER.REFERENCE";

    String GET_INCOMES_FAMILY_GROUP_BY_CATEGORIES = "SELECT SUM(SUMM.VALUE) AS AMOUNT, CATEGORY.LIST_VALUE_ID AS CATEGORY, CONSUMER.REFERENCE AS USER_ID " +
            "  FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF, OBJREFERENCE CONSUMER " +
            "  WHERE REF.ATTR_ID = 55 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ? " +
            "  AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 56 /* amount*/ " +
            "  AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 57 /* category*/ " +
            "  AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 58 /* date*/ " +
            "  AND CONSUMER.ATTR_ID = 54 AND O.OBJECT_ID = CONSUMER.OBJECT_ID " +
            "  AND DATE_OF.DATE_VALUE > ? " +
            "  GROUP BY CONSUMER.REFERENCE, CATEGORY.LIST_VALUE_ID";

    String GET_PERSONAL_FOR_DATE_BY_ACCOUNT_ID = "SELECT SUMM.VALUE AS AMOUNT, " +
            "       DATE_OF.DATE_VALUE AS DATE_IN, " +
            "       DECODE(O.OBJECT_TYPE_ID, " +
            "              10, CATEGORY.LIST_VALUE_ID " +
            "               ) AS CATEGORY_INCOME, " +
            "       DECODE(O.OBJECT_TYPE_ID, " +
            "              9, CATEGORY.LIST_VALUE_ID " +
            "               ) AS CATEGORY_EXPENSE " +
            "FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF_O " +
            "WHERE O.OBJECT_TYPE_ID IN (10, 9) AND REF_O.ATTR_ID IN (53, 47) " +
            "    AND O.OBJECT_ID = REF_O.OBJECT_ID AND REF_O.REFERENCE = ?  " +
            "    AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID IN (56, 50) /* amount*/ " +
            "    AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID IN (57, 51) /* category*/ " +
            "    AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID IN (58, 52) /* date*/ " +
            "    AND DATE_OF.DATE_VALUE > ADD_MONTHS(TO_DATE(SYSDATE,'DD-MM-YYYY HH24:MI:SS'), -(?)) " +
            "ORDER BY DATE_OF.DATE_VALUE DESC";

    String GET_FAMILY_FOR_DATE_BY_ACCOUNT_ID = "SELECT NAME.VALUE USERNAME,  SUMM.VALUE AS AMOUNT, " +
            "       DATE_OF.DATE_VALUE AS DATE_IN, " +
            "       DECODE(O.OBJECT_TYPE_ID, " +
            "              21, CATEGORY.LIST_VALUE_ID " +
            "               ) AS CATEGORY_INCOME, " +
            "       DECODE(O.OBJECT_TYPE_ID, " +
            "              20, CATEGORY.LIST_VALUE_ID " +
            "               ) AS CATEGORY_EXPENSE " +
            "FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, " +
            "     ATTRIBUTES NAME, OBJECTS USERS, OBJREFERENCE REF_O, OBJREFERENCE REF_USER " +
            "WHERE O.OBJECT_TYPE_ID IN (21, 20) AND REF_O.ATTR_ID IN (55, 48) " +
            "    AND O.OBJECT_ID = REF_O.OBJECT_ID AND REF_O.REFERENCE = ? " +
            "    AND REF_USER.ATTR_ID IN (54, 49) AND USERS.OBJECT_ID = REF_USER.REFERENCE AND REF_USER.OBJECT_ID = O.OBJECT_ID " +
            "    AND NAME.ATTR_ID = 5 AND NAME.OBJECT_ID = USERS.OBJECT_ID " +
            "    AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID IN (56, 50) /* amount*/ " +
            "    AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID IN (57, 51) /* category*/ " +
            "    AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID IN (58, 52) /* date*/ " +
            "    AND DATE_OF.DATE_VALUE > ADD_MONTHS(TO_DATE(SYSDATE,'DD-MM-YYYY HH24:MI:SS'), -(?)) " +
            "ORDER BY DATE_OF.DATE_VALUE DESC";

    String GET_FIRST_15_PERSONAL_HISTORY_BY_ACCOUNT_ID = " SELECT *\n" +
            "            FROM (SELECT SUMM.VALUE AS AMOUNT,  \n" +
            "                   TO_CHAR(DATE_OF.DATE_VALUE, 'DD-MM-YYYY HH24:MI') AS DATE_IN,  \n" +
            "                   DECODE(O.OBJECT_TYPE_ID,  \n" +
            "                          10, CATEGORY.LIST_VALUE_ID  \n" +
            "                           ) AS CATEGORY_INCOME,  \n" +
            "                   DECODE(O.OBJECT_TYPE_ID,  \n" +
            "                          9, CATEGORY.LIST_VALUE_ID  \n" +
            "                           ) AS CATEGORY_EXPENSE  \n" +
            "            FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF_O  \n" +
            "            WHERE O.OBJECT_TYPE_ID IN (10, 9) AND REF_O.ATTR_ID IN (53, 47)  \n" +
            "                AND O.OBJECT_ID = REF_O.OBJECT_ID AND REF_O.REFERENCE = ?   \n" +
            "                AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID IN (56, 50) /* amount*/  \n" +
            "                AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID IN (57, 51) /* category*/  \n" +
            "                AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID IN (58, 52) /* date*/  \n" +
            "                ORDER BY DATE_OF.DATE_VALUE DESC)\n" +
            "            where ROWNUM <= 10";

    String GET_FIRST_15_FAMILY_HISTORY_BY_ACCOUNT_ID = "SELECT *\n" +
            "           FROM (SELECT NAME.VALUE USERNAME,  SUMM.VALUE AS AMOUNT,  \n" +
            "                   TO_CHAR(DATE_OF.DATE_VALUE, 'DD-MM-YYYY HH24:MI') AS DATE_IN,  \n" +
            "                   DECODE(O.OBJECT_TYPE_ID,  \n" +
            "                          21, CATEGORY.LIST_VALUE_ID  \n" +
            "                           ) AS CATEGORY_INCOME,  \n" +
            "                   DECODE(O.OBJECT_TYPE_ID,  \n" +
            "                          20, CATEGORY.LIST_VALUE_ID  \n" +
            "                           ) AS CATEGORY_EXPENSE  \n" +
            "            FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O,  \n" +
            "                 ATTRIBUTES NAME, OBJECTS USERS, OBJREFERENCE REF_O, OBJREFERENCE REF_USER  \n" +
            "            WHERE O.OBJECT_TYPE_ID IN (21, 20) AND REF_O.ATTR_ID IN (55, 48)  \n" +
            "                AND O.OBJECT_ID = REF_O.OBJECT_ID AND REF_O.REFERENCE = ?  \n" +
            "                AND REF_USER.ATTR_ID IN (54, 49) AND USERS.OBJECT_ID = REF_USER.REFERENCE AND REF_USER.OBJECT_ID = O.OBJECT_ID  \n" +
            "                AND NAME.ATTR_ID = 5 AND NAME.OBJECT_ID = USERS.OBJECT_ID  \n" +
            "                AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID IN (56, 50) /* amount*/  \n" +
            "                AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID IN (57, 51) /* category*/  \n" +
            "                AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID IN (58, 52) /* date*/   \n" +
            "                ORDER BY DATE_OF.DATE_VALUE DESC)\n" +
            "            WHERE ROWNUM <= 10";
}
