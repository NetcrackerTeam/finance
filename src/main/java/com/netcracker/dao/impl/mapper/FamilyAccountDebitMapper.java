package com.netcracker.dao.impl.mapper;

import com.netcracker.models.FamilyDebitAccount;
import org.springframework.jdbc.core.RowMapper;


import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FamilyAccountDebitMapper implements RowMapper<FamilyDebitAccount> {


    @Override
    public FamilyDebitAccount mapRow(ResultSet rs, int i) throws SQLException {
        return  (FamilyDebitAccount) new  FamilyDebitAccount.Builder()
            .debitId(new BigInteger(rs.getString("DEBIT_ID")))
            .debitObjectName(rs.getString("NAME"))
            .debitAmount(rs.getLong("values"))
           // .debitAccountExpensesList()
           // .debitAccountIncomesList()
           // .debitOwner((User)rs.getObject("reference"));
    }
}
