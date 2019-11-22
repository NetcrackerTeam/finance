package com.netcracker.dao.impl.mapper;

import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.User;
import org.springframework.jdbc.core.RowMapper;


import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FamilyAccountDebitMapper implements RowMapper<FamilyDebitAccount> {


    @Override
    public FamilyDebitAccount mapRow(ResultSet rs, int i) throws SQLException {
        return new FamilyDebitAccount.Builder()
        .debitId(new BigInteger(rs.getString("debit_id")))
        .debitObjectName(rs.getString("NAME"))
        .debitAmount((rs.getLong("values"))).build();
       // .debitOwner(new User.Builder())
    }
}
