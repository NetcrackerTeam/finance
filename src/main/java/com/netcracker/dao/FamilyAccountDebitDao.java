package com.netcracker.dao;
import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.User;


import java.math.BigInteger;
import java.util.List;

public interface FamilyAccountDebitDao {
    FamilyDebitAccount getFamilyAccountById(BigInteger id);

    FamilyDebitAccount createFamilyAccount(FamilyDebitAccount familyDebitAccount);

    void deleteFamilyAccount(BigInteger id);

    void addUserToAccountById(BigInteger user_id, BigInteger account_id);

    void deleteUserFromAccountById(BigInteger account_id, BigInteger user_id);

    List<User> getParticipantsOfFamilyAccount(BigInteger debit_id);

    List<AccountIncome> getIncomesOfFamilyAccount(BigInteger debit_id);

    List<AccountExpense> getExpensesOfFamilyAccount(BigInteger debit_id);

    String ADD_USER_BY_ID = "INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,?,?)";

    String FIND_FAMILY_ACCOUNT_BY_ID = "SELECT " +
            "debit.object_id as debit_id, debit.name as name_debit, attr1.value as amount_debit, attr2.list_value_id as status_debit, "
            +
            "us.object_id as USER_ID, attr1_user.value as NAME, attr2_user.value as EMAIL, attr3_user.value as PASSWORD "
            +
                "from OBJECTS debit, attributes attr1, attributes attr2,  "
            +
                "objects us, attributes attr1_user, attributes attr2_user, attributes attr3_user, OBJREFERENCE objref "
            +
                    "where debit.object_type_id = 13 and us.object_type_id = 1 " +
                    "and debit.object_id = ? " +
                    "and attr1.object_id = debit.object_id " +
                    "and attr1.attr_id = 9 " +
                    "and attr2.object_id = debit.object_id " +
                    "and attr2.attr_id = 69 " +
                        "and objref.attr_id = 2 " +
                        "and objref.reference = debit.object_id " +
                        "and us.object_id = objref.object_id " +
                        "and attr1_user.object_id = us.object_id " +
                        "and attr1_user.attr_id = 5 " +
                        "and attr2_user.object_id = us.object_id " +
                        "and attr2_user.attr_id = 3 " +
                        "and attr3_user.object_id = us.object_id " +
                        "and attr3_user.attr_id = 4 ";

    String ADD_NEW_FAMILY_ACCOUNT = "INSERT ALL " +
            "INTO OBJECTS(OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (objects_id_s.nextval, 13, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(9, objects_id_s.currval, ?) "
            +
            "INTO ATTRIBUTES(ATTR_ID, OBJECT_ID, VALUE) VALUES(69, objects_id_s.currval, ?) ";
    String DELETE_USER_FROM_FAMILY_ACCOUNT = "DELETE FROM OBJREFERENCE WHERE ATTR_ID = 8, OBJECT_ID = ?, REFERENCE = ?";

    String SET_FAMILY_ACCOUNT_UNACTIVE = "update attributes set list_value_id = 42 where attr_id = 69 and object_id = ?";

    // String CHEK_USER_ACTIVE_AND_REFERENCED = "SELECT reference FROM objreference WHERE attr_id = 8 and object_id = ? and reference = ?";

    String GET_PARTICIPANTS = "SELECT\n" +
           "\tus_part.object_id as USER_ID, attr1_us_part.value as NAME, attr2_us_part.value as EMAIL, attr3_us_part.value as PASSWORD  \n" +
           "\t\tfrom OBJECTS debit, objects us_part, attributes attr1_us_part, attributes attr2_us_part, attributes attr3_us_part, OBJREFERENCE objref  \t\t\t\t\t\n" +
           "\t\t\twhere debit.object_type_id = 13 and us_part.object_type_id = 1\n" +
           "\t\t\tand debit.object_id = 3\n" +
           "\t\t\t\tand objref.attr_id = 8\n" +
           "\t\t\t\tand objref.object_id = debit.object_id\n" +
           "\t\t\t\tand us_part.object_id = objref.reference\n" +
           "\t\t\t\tand attr1_us_part.object_id = us_part.object_id\n" +
           "\t\t\t\tand attr1_us_part.attr_id = 5\n" +
           "\t\t\t\tand attr2_us_part.object_id = us_part.object_id\n" +
           "\t\t\t\tand attr2_us_part.attr_id = 3\n" +
           "\t\t\t\tand attr3_us_part.object_id = us_part.object_id\n" +
           "\t\t\t\tand attr3_us_part.attr_id = 4";

    String GET_INCOME_LIST = "SELECT \n" +
            "\tincome.object_id as account_income_id, attr1.value as income_amount, attr2.date_value as date_income, us.object_id as user_id \n" +
            "    from OBJECTS income,  objects us, attributes attr1, attributes attr2, objects debit, OBJREFERENCE objref1, OBJREFERENCE objref2 \t\t\t\t\t\n" +
            "\t\t\twhere debit.object_type_id = 13 and us.object_type_id = 1 and income.object_type_id = 21\n" +
            "                and debit.object_id = ?\n" +
            "\t\t\t\tand objref1.attr_id = 54\n" +
            "\t\t\t\tand objref1.reference = debit.object_id\n" +
            "\t\t\t\tand income.object_id = objref1.object_id\n" +
            "                and objref2.attr_id = 55\n" +
            "\t\t\t\tand objref2.object_id = income.object_id\n" +
            "\t\t\t\tand us.object_id = objref2.reference\n" +
            "\t\t\t\tand attr1.object_id = income.object_id\n" +
            "\t\t\t\tand attr1.attr_id = 56\n" +
            "\t\t\t\tand attr2.object_id = income.object_id\n" +
            "\t\t\t\tand attr2.attr_id = 58\n";

    String GET_EXPENSE_LIST = "SELECT \n" +
            "\texpense.object_id as account_income_id, attr1.value as expense_amount, attr2.date_value as date_expense, us.object_id as user_id \n" +
            "    from OBJECTS expense,  objects us, attributes attr1, attributes attr2, objects debit, OBJREFERENCE objref1, OBJREFERENCE objref2 \t\t\t\t\t\n" +
            "\t\t\twhere debit.object_type_id = 13 and us.object_type_id = 1 and expense.object_type_id = 20\n" +
            "                and debit.object_id = ?\n" +
            "\t\t\t\tand objref1.attr_id = 48\n" +
            "\t\t\t\tand objref1.reference = debit.object_id\n" +
            "\t\t\t\tand expense.object_id = objref1.object_id\n" +
            "                and objref2.attr_id = 49\n" +
            "\t\t\t\tand objref2.object_id = expense.object_id\n" +
            "\t\t\t\tand us.object_id = objref2.reference\n" +
            "\t\t\t\tand attr1.object_id = expense.object_id\n" +
            "\t\t\t\tand attr1.attr_id = 50\n" +
            "\t\t\t\tand attr2.object_id = expense.object_id\n" +
            "\t\t\t\tand attr2.attr_id = 52\n";
}
