package com.netcracker.dao;

import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.enums.CategoryExpense;
import com.netcracker.models.enums.CategoryIncome;
import oracle.sql.BINARY_DOUBLE;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OperationDao {


    void addIncomePersonalByAccId(BigInteger id, BigDecimal income, LocalDate date, CategoryIncome categoryIncome);

    void addExpensePersonaByAccId(BigInteger id, BigDecimal expense, LocalDate date, CategoryExpense categoryExpense);

    void addIncomeFamilyByAccId(BigInteger idUser, BigInteger idFamily, BigDecimal income, LocalDate date, CategoryIncome categoryIncome);

    void addExpenseFamilyByAccId(BigInteger id, BigInteger idFamily, BigDecimal expense, LocalDate date, CategoryExpense categoryExpense);

    Collection<AccountIncome> getIncomesPersonalAfterDateByAccountId(BigInteger id, Date date);

    Collection<AccountExpense> getExpensesPersonalAfterDateByAccountId(BigInteger id, Date date);

    Collection<AccountIncome> getIncomesFamilyAfterDateByAccountId(BigInteger id, Date data);

    Collection<AccountExpense> getExpensesFamilyAfterDateByAccountId(BigInteger id, Date data);

    String ADD_INCOME_PERSONAL_BY_ACCOUNT_ID = "INSERT ALL\n" +
            "         INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,10,'ACC_INC_PER1','account income personal1')\n" +
            "         INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (53,OBJECTS_ID_S.currval,?)\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(56, OBJECTS_ID_S.currval, ?)\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(58, OBJECTS_ID_S.currval, TO_DATE(TO_CHAR(?), 'DD-MM-YYYY'))\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(57, OBJECTS_ID_S.currval, ?)\n" +
            "         SELECT * FROM DUAL;";
    String ADD_EXPENSE_PERSONAL_BY_ACCOUNT_ID = "INSERT ALL\n" +
            "         INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (OBJECTS_ID_S.nextval,NULL,9,'account_expense_personal','account_expense_personal')\n" +
            "         INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (47,OBJECTS_ID_S.currval,?) /* id */\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(50, OBJECTS_ID_S.currval, ?) /* amount */\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(52, OBJECTS_ID_S.currval, TO_DATE(TO_CHAR(?), 'DD-MM-YYYY')) /* date */\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(51, OBJECTS_ID_S.currval, ?) /* category */\n" +
            "         SELECT * FROM DUAL;";

    String ADD_INCOME_FAMILY_BY_ACCOUNT_ID = "INSERT ALL\n" +
            "         INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,10,'ACC_INC_PER1','account income personal1')\n" +
            "         INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (53,OBJECTS_ID_S.currval,?)\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(56, OBJECTS_ID_S.currval, ?) /* amount */\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(58, OBJECTS_ID_S.currval, TO_DATE(TO_CHAR(?), 'DD-MM-YYYY')) /* date */\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(57, OBJECTS_ID_S.currval, ?) /* category */\n" +
            "         SELECT * FROM DUAL;";
    String ADD_EXPENSE_FAMILY_BY_ACCOUNT_ID = "INSERT ALL\n" +
            "         INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (OBJECTS_ID_S.nextval,NULL,20,'account_expense_family','account expense Family1')\n" +
            "         INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (55, OBJECTS_ID_S.currval,?) /* reference to user*/\n" +
            "         INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (54,OBJECTS_ID_S.currval,?) /* reference to family*/\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(56, OBJECTS_ID_S.currval, TO_CHAR(?)) /* amount */\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, DATE_VALUE) VALUES(58, OBJECTS_ID_S.currval, TO_DATE(TO_CHAR(?), 'DD-MM-YYYY')) /* date */\n" +
            "         INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, LIST_VALUE_ID) VALUES(57, OBJECTS_ID_S.currval, 14) /* category */\n" +
            "         SELECT * FROM DUAL;";

    String GET_INCOMES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID = "SELECT O.OBJECT_ID,  SUMM.VALUE, DATE_OF.DATE_VALUE, LIST.VALUE\n" +
            "FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF, LISTS LIST\n" +
            "WHERE O.OBJECT_TYPE_ID =  10 AND O.OBJECT_ID = REF.REFERENCE AND REF.OBJECT_ID = ?\n" +
            "  AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 56 /* amount*/\n" +
            "  AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 57 /* category*/\n" +
            "  AND LIST.ATTR_ID = CATEGORY.ATTR_ID AND LIST.LIST_VALUE_ID = CATEGORY.LIST_VALUE_ID\n" +
            "  AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 58 /* date*/\n" +
            "  AND DATE_OF.DATE_VALUE > ?;\n";
    String GET_EXPENSES_PERSONAL_AFTER_DATE_BY_ACCOUNT_ID = "SELECT O.OBJECT_ID,  SUMM.VALUE, DATE_OF.DATE_VALUE, LIST.VALUE\n" +
            "FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF, LISTS LIST\n" +
            "WHERE O.OBJECT_TYPE_ID =  9 AND O.OBJECT_ID = REF.REFERENCE AND REF.OBJECT_ID = ?\n" +
            "  AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 56 /* amount*/\n" +
            "  AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 57 /* category*/\n" +
            "  AND LIST.ATTR_ID = CATEGORY.ATTR_ID AND LIST.LIST_VALUE_ID = CATEGORY.LIST_VALUE_ID\n" +
            "  AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 58 /* date*/\n" +
            "  AND DATE_OF.DATE_VALUE > ?;\n";

    String GET_INCOMES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID = "SELECT O.OBJECT_ID,  SUMM.VALUE, DATE_OF.DATE_VALUE, LIST.VALUE\n" +
            "FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF, LISTS LIST\n" +
            "WHERE O.OBJECT_TYPE_ID =  21 AND O.OBJECT_ID = REF.REFERENCE AND REF.OBJECT_ID = ?\n" +
            "  AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 56 /* amount*/\n" +
            "  AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 57 /* category*/\n" +
            "  AND LIST.ATTR_ID = CATEGORY.ATTR_ID AND LIST.LIST_VALUE_ID = CATEGORY.LIST_VALUE_ID\n" +
            "  AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 58 /* date*/\n" +
            "  AND DATE_OF.DATE_VALUE > ?;";
    String GET_EXPENSES_FAMILY_AFTER_DATE_BY_ACCOUNT_ID = "SELECT O.OBJECT_ID,  SUMM.VALUE, DATE_OF.DATE_VALUE, LIST.VALUE\n" +
            "FROM ATTRIBUTES SUMM, ATTRIBUTES DATE_OF, ATTRIBUTES CATEGORY, OBJECTS O, OBJREFERENCE REF, LISTS LIST\n" +
            "WHERE O.OBJECT_TYPE_ID =  20 AND O.OBJECT_ID = REF.REFERENCE AND REF.OBJECT_ID = ?\n" +
            "  AND SUMM.OBJECT_ID = O.OBJECT_ID AND SUMM.ATTR_ID = 50 /* amount*/\n" +
            "  AND CATEGORY.OBJECT_ID = O.OBJECT_ID AND CATEGORY.ATTR_ID = 51 /* category*/\n" +
            "  AND LIST.ATTR_ID = CATEGORY.ATTR_ID AND LIST.LIST_VALUE_ID = CATEGORY.LIST_VALUE_ID\n" +
            "  AND DATE_OF.OBJECT_ID = O.OBJECT_ID AND DATE_OF.ATTR_ID = 52 /* date*/\n" +
            "  AND DATE_OF.DATE_VALUE > ?;";

}
