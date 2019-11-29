package com.netcracker.dao;

import com.netcracker.models.CreditOperation;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface CreditOperationDao {
    CreditOperation getCreditOperationPersonal(Integer creditOperationId);

    CreditOperation getCreditOperationFamily(Integer creditOperationId);

    CreditOperation createFamilyCreditOperation(CreditOperation creditOperation, Integer creditFamilyAccountId, Integer userId);

    CreditOperation createPersonalCreditOperation(CreditOperation creditOperation, Integer creditPersonalAccountId);

    Collection<CreditOperation> getAllCreditOperationsByCreditFamilyId(Integer creditFamilyAccountId);

    Collection<CreditOperation> getAllCreditOperationsByCreditPersonalId(Integer creditPersonalAccountId);

    void deleteCreditOperation(Integer creditOperationId);

    String CREATE_CREDIT_OPERATION_PERSONAL = "insert all " +
            "into objects (object_id, parent_id, object_type_id, name, description) " +
            "values (objects_id_s.nextval, null, 7, 'credit_operation_personal', null) " +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (40 /*amount*/, objects_id_s.currval, ? /*amount*/, null, null) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (41 /*date*/, objects_id_s.currval, null, ?, null) \n" +
            "into objreference (attr_id, object_id, reference) " +
            "values (37, objects_id_s.currval, ? /*reference to credit_acc_personal*/) " + "select * from dual";

    String CREATE_CREDIT_OPERATION_FAMILY = "insert all " +
            "into objects (object_id, parent_id, object_type_id, name, description) " +
            "values (objects_id_s.nextval, null, 18, 'credit_operation_family', null) " +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (40 /*amount*/, objects_id_s.currval, ? /*amount*/, null, null) \n" +
            "into attributes (attr_id, object_id, value, date_value, list_value_id) " +
            "values (41 /*date*/, objects_id_s.currval, null, ?, null) \n" +
            "into objreference (attr_id, object_id, reference) " +
            "values (38, objects_id_s.currval, ? /*reference to credit_acc_family*/) \n" +
            "into objreference (attr_id, object_id, reference) " +
            "values (39, objects_id_s.currval, ? /*reference to user*/) " + "select * from dual";

    String GET_CREDIT_OPERATION_PERSONAL =
            "select credit_acc_personal.object_id as credit_acc_personal_id, credit_acc_name.value as credit_acc_name, \n" +
            "\tcredit_operation.object_id as operation_id, credit_operation.name as operation_name, \n" +
            "\tamount.value as amount, dates.date_value as date_value \n" +
            "from objects credit_acc_personal, objects credit_operation, attributes credit_acc_name, \n" +
            "\tattributes amount, attributes dates, objreference credit_acc_ref_user\n" +
            "where credit_acc_ref_user.attr_id = 37 and credit_operation.object_id = ? \n" +
            "\tand credit_operation.object_id = credit_acc_ref_user.object_id \n" +
            "\tand credit_acc_personal.object_id = credit_acc_ref_user.reference \n" +
            "\tand credit_acc_name.attr_id = 30 and amount.attr_id = 40 and dates.attr_id = 41 \n" +
            "\tand credit_acc_name.object_id = credit_acc_personal.object_id \n" +
            "\tand amount.object_id = credit_operation.object_id and dates.object_id = credit_operation.object_id";

    String GET_CREDIT_OPERATION_FAMILY =
            "select credit_acc_family.object_id as credit_id_family, acc_personal.object_id as id_user, \n" +
            "  credit_acc_name.value as credit_acc_name, credit_operation.object_id as operation_id, \n" +
            "  credit_operation.name as operation_name, amount.value as amount, dates.date_value as date_value \n" +
            "from objects credit_acc_family, objects acc_personal, objects credit_operation, attributes credit_acc_name, \n" +
            "  attributes amount, attributes dates, objreference acc_ref_user, objreference credit_acc_ref_family\n" +
            "where credit_operation.object_id = ? \n" +
            "  and credit_acc_ref_family.attr_id = 38 and acc_ref_user.attr_id = 39\n" +
            "  and credit_operation.object_id = credit_acc_ref_family.object_id \n" +
            "  and credit_acc_family.object_id = credit_acc_ref_family.reference \n" +
            "  and credit_operation.object_id = acc_ref_user.object_id \n" +
            "  and acc_personal.object_id = acc_ref_user.reference \n" +
            "  and credit_acc_name.attr_id = 30 and amount.attr_id = 40 and dates.attr_id = 41 \n" +
            "  and credit_acc_name.object_id = credit_acc_family.object_id \n" +
            "  and amount.object_id = credit_operation.object_id and dates.object_id = credit_operation.object_id";


    String DELETE_FROM_OBJECTS = "delete from objects where object_id = ?";
    String DELETE_FROM_ATTRIBUTES = "delete from attributes where object_id = ?";
    String DELETE_FROM_OBJREFERENCE = "delete from objreference where object_id = ?";

    String GET_ALL_CREDIT_OPERATIONS_PERSONAL =
            "select credit_acc_personal.object_id as credit_acc_personal_id, credit_acc_name.value as credit_acc_name, \n" +
            "  credit_operation.object_id as operation_id, credit_operation.name as operation_name, \n" +
            "  amount.value as amount, dates.date_value as date_value \n" +
            "from objects credit_acc_personal, objects credit_operation, attributes credit_acc_name, \n" +
            "  attributes amount, attributes dates, objreference credit_acc_ref_user\n" +
            "where credit_acc_personal.object_id = ? and credit_acc_ref_user.attr_id = 37\n" +
            "  and credit_operation.object_id = credit_acc_ref_user.object_id \n" +
            "  and credit_acc_personal.object_id = credit_acc_ref_user.reference \n" +
            "  and credit_acc_name.attr_id = 30 and amount.attr_id = 40 and dates.attr_id = 41 \n" +
            "  and credit_acc_name.object_id = credit_acc_personal.object_id \n" +
            "  and amount.object_id = credit_operation.object_id and dates.object_id = credit_operation.object_id";

    String GET_ALL_CREDIT_OPERATIONS_FAMILY =
            "select credit_acc_family.object_id as credit_id_family, acc_personal.object_id as id_user, \n" +
            "  credit_acc_name.value as credit_acc_name, credit_operation.object_id as operation_id, \n" +
            "  credit_operation.name as operation_name, amount.value as amount, dates.date_value as date_value \n" +
            "from objects credit_acc_family, objects acc_personal, objects credit_operation, attributes credit_acc_name, \n" +
            "  attributes amount, attributes dates, objreference acc_ref_user, objreference credit_acc_ref_family\n" +
            "where credit_acc_family.object_id = ? \n" +
            "  and credit_acc_ref_family.attr_id = 38 and acc_ref_user.attr_id = 39\n" +
            "  and credit_operation.object_id = credit_acc_ref_family.object_id \n" +
            "  and credit_acc_family.object_id = credit_acc_ref_family.reference \n" +
            "  and credit_operation.object_id = acc_ref_user.object_id \n" +
            "  and acc_personal.object_id = acc_ref_user.reference \n" +
            "  and credit_acc_name.attr_id = 30 and amount.attr_id = 40 and dates.attr_id = 41 \n" +
            "  and credit_acc_name.object_id = credit_acc_family.object_id \n" +
            "  and amount.object_id = credit_operation.object_id and dates.object_id = credit_operation.object_id";
}
