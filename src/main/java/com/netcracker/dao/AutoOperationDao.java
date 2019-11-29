package com.netcracker.dao;

import com.netcracker.models.AbstractAutoOperation;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;

import java.math.BigInteger;
import java.util.List;

public interface AutoOperationDao {
    AutoOperationIncome getFamilyIncomeAutoOperation(Integer autoOperationId);

    AutoOperationExpense getFamilyExpenseAutoOperation(Integer autoOperationId);

    AutoOperationIncome getPersonalIncomeAutoOperation(Integer autoOperationId);

    AutoOperationExpense getPersonalExpenseAutoOperation(Integer autoOperationId);

    AutoOperationIncome createFamilyIncomeAutoOperation(AutoOperationIncome autoOperationIncome);

    AutoOperationIncome createPersonalIncomeAutoOperation(AutoOperationIncome autoOperationIncome);

    AutoOperationExpense createFamilyExpenseAutoOperation(AutoOperationExpense autoOperationExpense);

    AutoOperationExpense createPersonalExpenseAutoOperation(AutoOperationExpense autoOperationExpense);

    void deleteAutoOperation(Integer autoOperationId);

    List<AbstractAutoOperation> getAllTodayOperations(Integer debitAccountId);

    String CREATE_PERSONAL_INCOME_AO = "insert all " +
            "into objects (object_id, parent_id, object_type_id, name, description) " +
            "values (objects_id_s.nextval, null, 12, 'personal_income_ao', null) " +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (67, objects_id_s.currval /*personal_income*/, ? /*day_of_month*/, null, null) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (56 /*amount*/, objects_id_s.currval, ? /*amount*/, null, null) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (57 /*category*/, objects_id_s.currval, null, null, ? /*32-36*/) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (58 /*current_date*/, objects_id_s.currval, null, trunc(current_date), null) \n" +
            "into objreference (attr_id, object_id, reference) values " +
            "(64, objects_id_s.currval /*expense_object_id*/, ? /*personal_debit_acc_object_id*/) " + "select * from dual";

    String CREATE_PERSONAL_EXPENSE_AO = "insert all " +
            "into objects (object_id, parent_id, object_type_id, name, description) " +
            "values (objects_id_s.nextval, null, 11, 'personal_expense_ao', null) " +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (62, objects_id_s.currval /*personal_expense*/, ? /*day_of_month*/, null, null) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (56 /*amount*/, objects_id_s.currval, ? /*amount*/, null, null) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (57 /*category*/, objects_id_s.currval, null, null, ? /*14-18*/) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (58 /*current_date*/, objects_id_s.currval, null, trunc(current_date), null) \n" +
            "into objreference (attr_id, object_id, reference) " +
            "values (59, objects_id_s.currval /*expense_object_id*/, ? /*personal_debit_acc_object_id*/) " + "select * from dual";

    String CREATE_FAMILY_INCOME_AO = "insert all " +
            "into objects (object_id, parent_id, object_type_id, name, description) " +
            "values (objects_id_s.nextval, null, 23, 'family_income_ao', null) " +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (68, objects_id_s.currval /*family_income*/, ? /*day_of_month*/, null, null) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (56 /*amount*/, objects_id_s.currval, ? /*amount*/, null, null) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (57 /*category*/, objects_id_s.currval, null, null, ? /*32-36*/) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (58 /*current_date*/, objects_id_s.currval, null, trunc(current_date), null) \n" +
            "into objreference (attr_id, object_id, reference) " +
            "values (65, objects_id_s.currval /*income_object_id*/, ? /*family_debit_acc_object_id*/) \n" +
            "into objreference (attr_id, object_id, reference) " +
            "values (66 /*transaction_auto_income*/, objects_id_s.currval, ? /*reference_to_user*/) " + "select * from dual";

    String CREATE_FAMILY_EXPENSE_AO = "insert all " +
            "into objects (object_id, parent_id, object_type_id, name, description) " +
            "values (objects_id_s.nextval, null, 22, 'family_expense_ao', null) " +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (63, objects_id_s.currval /*family_expense*/, ? /*day_of_month*/, null, null) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (56 /*amount*/, objects_id_s.currval, ? /*amount*/, null, null) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (57 /*category*/, objects_id_s.currval, null, null, ? /*14-18*/) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (58 /*current_date*/, objects_id_s.currval, null, trunc(current_date), null) \n" +
            "into objreference (attr_id, object_id, reference) " +
            "values (60, objects_id_s.currval /*expense_object_id*/, ? /*family_debit_acc_object_id*/) \n" +
            "into objreference (attr_id, object_id, reference) " +
            "values (61 /*transaction_auto_expense*/, objects_id_s.currval, ? /*reference_to_user*/) " + "select * from dual";

    String DELETE_FROM_OBJECTS = "delete from objects where object_id = ?";
    String DELETE_FROM_ATTRIBUTES = "delete from attributes where object_id = ?";
    String DELETE_FROM_OBJREFERENCE = "delete from objreference where object_id = ?";

    int personalExpense_user_debit_acc_ref_attr_id_1 = 59;
    int personalExpense_day_of_month_attr_id_3 = 62;
    int personalIncome_user_debit_acc_ref_attr_id_1 = 64;
    int personalIncome_day_of_month_attr_id_3 = 67;

    int familyExpense_family_ref_attr_id_1 = 60;
    int familyExpense_users_ref_attr_id_2 = 61;
    int familyExpense_day_of_month_attr_id_4 = 63;
    int familyIncome_family_ref_attr_id_1 = 65;
    int familyIncome_users_ref_attr_id_2 = 66;
    int familyIncome_day_of_month_attr_id_4 = 68;

    String GET_PERSONAL_AO = "select auto_operation.object_id as ao_object_id, auto_operation.name, user_debit_acc.object_id as user_debit_acc_id, \n" +
            "  users.object_id as user_id, \n" +
            "  email.value as email, user_name.value as user_name, day_of_month.value as day_of_month, amount.value as amount, \n" +
            "  category.list_value_id as category_id, lists.value as category, dates.date_value as date_of_creation\n" +
            "from objects auto_operation, objects user_debit_acc, objects users, lists, objreference user_debit_acc_ref,\n" +
            "  attributes amount, \n" +
            "  attributes category, attributes dates, attributes day_of_month, attributes email, attributes user_name, \n" +
            "  objreference users_ref \n" +
            "where user_debit_acc_ref.attr_id = ? and auto_operation.object_id = ? \n" +
            "  and auto_operation.object_id = user_debit_acc_ref.object_id \n" +
            "  and user_debit_acc.object_id = user_debit_acc_ref.reference\n" +
            "  and users.object_id = users_ref.object_id and user_debit_acc.object_id = users_ref.reference \n" +
            "  and day_of_month.attr_id = ? and amount.attr_id = 56 \n" +
            "  and category.attr_id = 57 and dates.attr_id = 58 \n" +
            "  and email.attr_id = 3 and user_name.attr_id = 5\n" +
            "  and category.list_value_id = lists.list_value_id\n" +
            "  and day_of_month.object_id = auto_operation.object_id and amount.object_id = auto_operation.object_id \n" +
            "  and category.object_id = auto_operation.object_id and dates.object_id = auto_operation.object_id \n" +
            "  and email.object_id = users.object_id and user_name.object_id = users.object_id";

    String GET_FAMILY_AO = "select auto_operation.object_id as ao_object_id, auto_operation.name, \n" +
            "\tfamily_debit_acc.object_id as family_debit_acc_id, \n" +
            "\tusers.object_id as user_id, \n" +
            "\temail.value as email, user_name.value as user_name, \n" +
            "\tday_of_month.value as day_of_month, amount.value as amount, \n" +
            "\tcategory.list_value_id as category_id, lists.value as category, dates.date_value as date_of_creation\n" +
            "from objects auto_operation, objects family_debit_acc, objects users, lists,\n" +
            "\tattributes amount, attributes category, attributes dates, attributes day_of_month, attributes email, \n" +
            "\tattributes user_name, objreference family_ref, objreference users_ref \n" +
            "where family_ref.attr_id = ? and users_ref.attr_id = ? \n" +
            "\tand auto_operation.object_id = ? \n" +
            "\tand auto_operation.object_id = family_ref.object_id and family_debit_acc.object_id = family_ref.reference \n" +
            "\tand auto_operation.object_id = users_ref.object_id and users.object_id = users_ref.reference \n" +
            "\tand day_of_month.attr_id = ? and amount.attr_id = 56 \n" +
            "\tand category.attr_id = 57 and dates.attr_id = 58 \n" +
            "\tand email.attr_id = 3 and user_name.attr_id = 5\n" +
            "\tand category.list_value_id = lists.list_value_id \n" +
            "\tand day_of_month.object_id = auto_operation.object_id and amount.object_id = auto_operation.object_id \n" +
            "\tand category.object_id = auto_operation.object_id and dates.object_id = auto_operation.object_id \n" +
            "\tand email.object_id = users.object_id and user_name.object_id = users.object_id";

    String GET_ALL_TODAY_AO_INCOME = "select auto_operation.object_id as ao_object_id, auto_operation.name, debit_account.object_id as debit_id, \n" +
            "  day_of_month.value as day_of_month, amount.value as amount, category.list_value_id as category_id, \n" +
            "  lists.value as category, dates.date_value as date_of_creation\n" +
            "from objects auto_operation, objects debit_account, lists, attributes amount, attributes category, attributes dates,\n" +
            "  objreference, attributes day_of_month \n" +
            "where debit_account.object_id = ? and day_of_month.attr_id = 67 \n" +
            "  and dates.date_value = trunc(current_date)\n" +
            "  and auto_operation.object_id = objreference.object_id and debit_account.object_id = objreference.reference \n" +
            "  and amount.attr_id = 56 and category.list_value_id = lists.list_value_id \n" +
            "  and category.attr_id = 57 and dates.attr_id = 67 \n" +
            "  and day_of_month.object_id = auto_operation.object_id and amount.object_id = auto_operation.object_id \n" +
            "  and category.object_id = auto_operation.object_id and dates.object_id = auto_operation.object_id\n" +
            "UNION ALL\n" +
            "select auto_operation.object_id as ao_object_id, auto_operation.name, debit_account.object_id as debit_id, \n" +
            "  day_of_month.value as day_of_month, amount.value as amount, category.list_value_id as category_id, \n" +
            "  lists.value as category, dates.date_value as date_of_creation\n" +
            "from objects auto_operation, objects debit_account, lists, attributes amount, attributes category, attributes dates,\n" +
            "  objreference, attributes day_of_month \n" +
            "where debit_account.object_id = ? and day_of_month.attr_id = 68\n" +
            "  and dates.date_value = trunc(current_date)\n" +
            "  and auto_operation.object_id = objreference.object_id and debit_account.object_id = objreference.reference \n" +
            "  and amount.attr_id = 56 and category.list_value_id = lists.list_value_id \n" +
            "  and category.attr_id = 57 and dates.attr_id = 58 \n" +
            "  and day_of_month.object_id = auto_operation.object_id and amount.object_id = auto_operation.object_id \n" +
            "  and category.object_id = auto_operation.object_id and dates.object_id = auto_operation.object_id";

    String GET_ALL_TODAY_AO_EXPENSE = "select auto_operation.object_id as ao_object_id, auto_operation.name, debit_account.object_id as debit_id, \n" +
            "  day_of_month.value as day_of_month, amount.value as amount, category.list_value_id as category_id, \n" +
            "  lists.value as category, dates.date_value as date_of_creation\n" +
            "from objects auto_operation, objects debit_account, lists, attributes amount, attributes category, attributes dates,\n" +
            "  objreference, attributes day_of_month \n" +
            "where debit_account.object_id = ? and day_of_month.attr_id = 62\n" +
            "  and dates.date_value = trunc(current_date)\n" +
            "  and auto_operation.object_id = objreference.object_id and debit_account.object_id = objreference.reference \n" +
            "  and amount.attr_id = 56 and category.list_value_id = lists.list_value_id \n" +
            "  and category.attr_id = 57 and dates.attr_id = 58 \n" +
            "  and day_of_month.object_id = auto_operation.object_id and amount.object_id = auto_operation.object_id \n" +
            "  and category.object_id = auto_operation.object_id and dates.object_id = auto_operation.object_id\n" +
            "UNION ALL\n" +
            "select auto_operation.object_id as ao_object_id, auto_operation.name, debit_account.object_id as debit_id, \n" +
            "  day_of_month.value as day_of_month, amount.value as amount, category.list_value_id as category_id, \n" +
            "  lists.value as category, dates.date_value as date_of_creation\n" +
            "from objects auto_operation, objects debit_account, lists, attributes amount, attributes category, attributes dates,\n" +
            "  objreference, attributes day_of_month \n" +
            "where debit_account.object_id = ? and day_of_month.attr_id = 63\n" +
            "  and dates.date_value = trunc(current_date)\n" +
            "  and auto_operation.object_id = objreference.object_id and debit_account.object_id = objreference.reference \n" +
            "  and amount.attr_id = 56 and category.list_value_id = lists.list_value_id \n" +
            "  and category.attr_id = 57 and dates.attr_id = 58 \n" +
            "  and day_of_month.object_id = auto_operation.object_id and amount.object_id = auto_operation.object_id \n" +
            "  and category.object_id = auto_operation.object_id and dates.object_id = auto_operation.object_id";
}
