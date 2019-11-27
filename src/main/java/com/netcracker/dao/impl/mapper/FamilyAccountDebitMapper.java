package com.netcracker.dao.impl.mapper;

import com.netcracker.models.AccountExpense;
import com.netcracker.models.AccountIncome;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.User;
import com.netcracker.models.enums.FamilyAccountStatusActive;
import org.springframework.jdbc.core.RowMapper;


import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FamilyAccountDebitMapper implements RowMapper<FamilyDebitAccount> {


    @Override
    public FamilyDebitAccount mapRow(ResultSet rs, int i) throws SQLException {

        UserDaoMapper userMapper = new UserDaoMapper();
        User user = userMapper.mapRow(rs, i);

//        UserDaoMapper list_user_mapper = new UserDaoMapper();
//        ArrayList<User> list_user = new ArrayList<>();
//        list_user.add(list_user_mapper.mapRow(rs, i));
//
//        AccountIncomeMapper incomeMapper = new AccountIncomeMapper();
//        ArrayList<AccountIncome> income = new ArrayList<>();
//        income.add(incomeMapper.mapRow(rs, i));
//
//
//        AccountExpenseMapper expenseMapper = new AccountExpenseMapper();
//        ArrayList<AccountExpense> expense = new ArrayList<>();
//        expense.add(expenseMapper.mapRow(rs, i));

        return new FamilyDebitAccount.Builder()
                .debitId(rs.getBigDecimal("debit_id").toBigInteger())
                .debitObjectName(rs.getString("name_debit"))
                .debitAmount(Long.valueOf(rs.getString("amount_debit")))
                .debitFamilyAccountStatus(FamilyAccountStatusActive.getStatusByKey(rs.getBigDecimal("status_debit").toBigInteger()))
                .debitOwner(user).build();
                //.debitAccountIncomesList(income)
                //.debitAccountExpensesList(expense).build();
                //.debitFamilyAccountList()
                //.debitFamilyUserList(list_user).build();

    }
}
