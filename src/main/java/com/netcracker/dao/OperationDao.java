package com.netcracker.dao;

import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OperationDao {

    /**
     *
     * @param id
     * @param income
     * @param date
     * @param categoryIncome
     */

    void addIncomePersonalByAccId(BigInteger id, BigDecimal income, Date date, CategoryIncome categoryIncome);

    /**
     *
     * @param id
     * @param expense
     * @param date
     * @param categoryExpense
     */

    void addExpensePersonaByAccId(BigInteger id, BigDecimal expense, Date date, CategoryExpense categoryExpense);

    /**
     *
     * @param idUser
     * @param idFamily
     * @param income
     * @param date
     * @param categoryIncome
     */

    void addIncomeFamilyByAccId(BigInteger idUser, BigInteger idFamily, BigDecimal income, Date date, CategoryIncome categoryIncome);

    /**
     *
     * @param idUser
     * @param idFamily
     * @param expense
     * @param date
     * @param categoryExpense
     */

    void addExpenseFamilyByAccId(BigInteger idUser, BigInteger idFamily, BigDecimal expense, Date date, CategoryExpense categoryExpense);

    /**
     *
     * @param id
     * @param date
     * @return
     */

    Collection<AccountIncome> getIncomesPersonalAfterDateByAccountId(BigInteger id, Date date);

    /**
     *
     * @param id
     * @param date
     * @return
     */

    Collection<AccountExpense> getExpensesPersonalAfterDateByAccountId(BigInteger id, Date date);

    /**
     *
     * @param id
     * @param data
     * @return
     */

    Collection<AccountIncome> getIncomesFamilyAfterDateByAccountId(BigInteger id, Date data);

    /**
     *
     * @param id
     * @param data
     * @return
     */

    Collection<AccountExpense> getExpensesFamilyAfterDateByAccountId(BigInteger id, Date data);

    Collection<AccountExpense> getExpensesPersonalGroupByCategories(BigInteger id, Date date);

    Collection<AccountExpense> getIncomesPersonalGroupByCategories(BigInteger id, Date date);

    Collection<AccountExpense> getExpensesFamilyGroupByCategories(BigInteger id, Date date);

    Collection<AccountExpense> getIncomesFamilyGroupByCategories(BigInteger id, Date date);

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
            " AND DATE_OF.DATE_VALUE > ?";

    String GET_EXPENSES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID = "SELECT O.OBJECT_ID AS ACCOUNT_EXPENSE_ID,  " +
            "SUMM.VALUE AS EXPENSE_AMOUNT, DATE_OF.DATE_VALUE AS DATE_EXPENSE, CATEGORY.LIST_VALUE_ID AS CATEGORY_EXPENSE" +
            " FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF" +
            " WHERE O.OBJECT_TYPE_ID = 9 AND REF.ATTR_ID = 47 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ?" +
            " AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 50 /* amount*/" +
            " AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 51 /* category*/" +
            " AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 52 /* date*/" +
            " AND DATE_OF.DATE_VALUE > ?";

    String GET_INCOMES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID = "SELECT O.OBJECT_ID AS ACCOUNT_INCOME_ID,  SUMM.VALUE AS INCOME_AMOUNT, " +
            "DATE_OF.DATE_VALUE AS DATE_INCOME, CATEGORY.LIST_VALUE_ID AS CATEGORY_INCOME" +
            " FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF" +
            " WHERE O.OBJECT_TYPE_ID =  21 AND REF.ATTR_ID = 54 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ?" +
            " AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 56 /* amount*/" +
            " AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 57 /* category*/" +
            " AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 58 /* date*/" +
            " AND DATE_OF.DATE_VALUE > ?";
    String GET_EXPENSES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID = "SELECT O.OBJECT_ID AS ACCOUNT_EXPENSE_ID,  SUMM.VALUE AS EXPENSE_AMOUNT," +
            " DATE_OF.DATE_VALUE AS DATE_EXPENSE, CATEGORY.LIST_VALUE_ID AS CATEGORY_EXPENSE" +
            " FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF" +
            " WHERE O.OBJECT_TYPE_ID =  20 AND REF.ATTR_ID = 48 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ?" +
            " AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 50 /* amount*/" +
            " AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 51 /* category*/" +
            " AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 52/* date*/" +
            " AND DATE_OF.DATE_VALUE > ?";

    String GET_EXPENSES_PERSONAL_GROUP_BY_CATEGORIES = "SELECT  SUM(SUMM.VALUE) AS EXPENSE_AMOUNT,  CATEGORY.LIST_VALUE_ID AS CATEGORY_EXPENSE" +
            " FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF" +
            " WHERE O.OBJECT_TYPE_ID = 9 AND REF.ATTR_ID = 47 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ?" +
            " AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 50 /* amount*/" +
            " AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 51 /* category*/" +
            " AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 52 /* date*/" +
            " AND DATE_OF.DATE_VALUE > ?" +
            " GROUP BY CATEGORY.LIST_VALUE_ID";

    String GET_INCOMES_PERSONAL_GROUP_BY_CATEGORIES = "SELECT SUM(SUMM.VALUE) AS INCOME_AMOUNT, CATEGORY.LIST_VALUE_ID AS CATEGORY_INCOME" +
            "  FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF" +
            "  WHERE O.OBJECT_TYPE_ID =  10 AND REF.ATTR_ID = 53 AND O.OBJECT_ID = REF.OBJECT_ID AND REF.REFERENCE = ?" +
            "  AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 56 /* amount*/" +
            "  AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 57 /* category*/" +
            "  AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 58 /* date*/" +
            "  AND DATE_OF.DATE_VALUE > ?" +
            "  GROUP BY CATEGORY.LIST_VALUE_ID";

}
