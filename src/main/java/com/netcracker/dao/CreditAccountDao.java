package com.netcracker.dao;

import com.netcracker.models.FamilyCreditAccount;
import com.netcracker.models.PersonalCreditAccount;

import java.math.BigInteger;
import java.util.List;

public interface CreditAccountDao {

    PersonalCreditAccount getPersonalCreditById(BigInteger id);

    FamilyCreditAccount getFamilyCreditById(BigInteger id);

    List<PersonalCreditAccount> getAllPersonalCreditsByAccountId(BigInteger id);

    List<FamilyCreditAccount> getAllFamilyCreditsByAccountId(BigInteger id);

    void addPersonalCreditPayment(BigInteger id, long amount);

    void addFamilyCreditPayment(BigInteger id, long amount);

    String SELECT_FAMILY_CREDIT_QUERY = "select cred.OBJECT_ID credit_id, name_at.value name, amount_at.value amount,\n" +
            "  paid_at.value paid, date_at.date_value date_cr,  rate_at.value credit_rate, \n" +
            "  date_to_at.date_value date_to, month_day_at.value month_day, is_paid_at.list_value_id is_paid,\n" +
            "  debt_date_from_at.date_value debt_from, debt_date_to_at.date_value debt_to,\n" +
            "  debt_amount_at.value debt_amount, debt.object_id debt_id\n" +
            "    from objects cred\n" +
            "        left join attributes name_at on cred.object_id = name_at.object_id\n" +
            "        left join attributes amount_at on cred.object_id = amount_at.object_id\n" +
            "        left join attributes paid_at on cred.object_id = paid_at.object_id\n" +
            "        left join attributes date_at on cred.object_id = date_at.object_id\n" +
            "        left join attributes rate_at on cred.object_id = rate_at.object_id\n" +
            "        left join attributes date_to_at on cred.object_id = date_to_at.object_id\n" +
            "        left join attributes month_day_at on cred.object_id = month_day_at.object_id\n" +
            "        left join attributes is_paid_at on cred.object_id = is_paid_at.object_id\n" +
            "        left join objreference debt_ref on cred.object_id = debt_ref.reference\n" +
            "        left join objects debt on debt_ref.object_id = debt.object_id\n" +
            "        left join attributes debt_date_from_at on debt.object_id = debt_date_from_at.object_id\n" +
            "        left join attributes debt_date_to_at on debt.object_id = debt_date_to_at.object_id\n" +
            "        left join attributes debt_amount_at on debt.object_id = debt_amount_at.object_id\n" +
            "        \n" +
            "    where cred.object_id = ?\n" +
            "        and debt.object_type_id = 19\n" +
            "        and date_at.attr_id = 29\n" +
            "        and name_at.attr_id = 30\n" +
            "        and amount_at.attr_id = 31\n" +
            "        and paid_at.attr_id = 32\n" +
            "        and rate_at.attr_id = 33\n" +
            "        and date_to_at.attr_id = 34\n" +
            "        and is_paid_at.attr_id = 35\n" +
            "        and month_day_at.attr_id = 36\n" +
            "        and debt_date_from_at.attr_id = 44\n" +
            "        and debt_date_to_at.attr_id = 45\n" +
            "        and debt_amount_at.attr_id = 46";

    String SELECT_PERSONAL_CREDIT_QUERY = "select cred.OBJECT_ID credit_id, name_at.value name, amount_at.value amount,\n" +
            "  paid_at.value paid, date_at.date_value date_cr,  rate_at.value credit_rate, \n" +
            "  date_to_at.date_value date_to, month_day_at.value month_day, is_paid_at.list_value_id is_paid,\n" +
            "  debt_date_from_at.date_value debt_from, debt_date_to_at.date_value debt_to,\n" +
            "  debt_amount_at.value debt_amount, debt.object_id debt_id\n" +
            "    from objects cred\n" +
            "        left join attributes name_at on cred.object_id = name_at.object_id\n" +
            "        left join attributes amount_at on cred.object_id = amount_at.object_id\n" +
            "        left join attributes paid_at on cred.object_id = paid_at.object_id\n" +
            "        left join attributes date_at on cred.object_id = date_at.object_id\n" +
            "        left join attributes rate_at on cred.object_id = rate_at.object_id\n" +
            "        left join attributes date_to_at on cred.object_id = date_to_at.object_id\n" +
            "        left join attributes month_day_at on cred.object_id = month_day_at.object_id\n" +
            "        left join attributes is_paid_at on cred.object_id = is_paid_at.object_id\n" +
            "        left join objreference debt_ref on cred.object_id = debt_ref.reference\n" +
            "        left join objects debt on debt_ref.object_id = debt.object_id\n" +
            "        left join attributes debt_date_from_at on debt.object_id = debt_date_from_at.object_id\n" +
            "        left join attributes debt_date_to_at on debt.object_id = debt_date_to_at.object_id\n" +
            "        left join attributes debt_amount_at on debt.object_id = debt_amount_at.object_id\n" +
            "        \n" +
            "    where cred.object_id = ?\n" +
            "        and debt.object_type_id = 8\n" +
            "        and date_at.attr_id = 29\n" +
            "        and name_at.attr_id = 30\n" +
            "        and amount_at.attr_id = 31\n" +
            "        and paid_at.attr_id = 32\n" +
            "        and rate_at.attr_id = 33\n" +
            "        and date_to_at.attr_id = 34\n" +
            "        and is_paid_at.attr_id = 35\n" +
            "        and month_day_at.attr_id = 36\n" +
            "        and debt_date_from_at.attr_id = 44\n" +
            "        and debt_date_to_at.attr_id = 45\n" +
            "        and debt_amount_at.attr_id = 46";
    String SELECT_PERSONAL_CREDITS_BY_ACCOUNT_QUERY = "";
    String SELECT_FAMILY_CREDITS_BY_ACCOUNT_QUERY = "";
    String ADD_PERSONAL_CREDIT_PAYMENT_QUERY = "";
    String ADD_FAMILY_CREDIT_PAYMENT_QUERY = "";
}
