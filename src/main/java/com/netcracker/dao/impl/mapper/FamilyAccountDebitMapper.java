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

//        UserDaoMapper userMapper = new UserDaoMapper();
//        User user = userMapper.mapRow(rs, i);
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
                .debitObjectName(rs.getString("name"))
                .debitAmount(Long.valueOf(rs.getString("amount")))
                .debitFamilyAccountStatus(FamilyAccountStatusActive.getStatusByKey(rs.getBigDecimal("status").toBigInteger())).build();
                //.debitOwner(user).build();
                //.debitAccountIncomesList(income)
                //.debitAccountExpensesList(expense).build();
                //.debitFamilyAccountList()
                //.debitFamilyUserList()

    }
}
