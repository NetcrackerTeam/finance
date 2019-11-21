package com.netcracker.dao.impl.mapper;

import com.netcracker.models.FamilyDebitAccount;
import org.springframework.jdbc.core.RowMapper;


import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FamilyAccountDebitMapper implements RowMapper<FamilyDebitAccount> {


    @Override
    public FamilyDebitAccount mapRow(ResultSet rs, int i) throws SQLException {
        FamilyDebitAccount familyDebitAccount = new FamilyDebitAccount();
        familyDebitAccount.setId(new BigInteger(Integer.valueOf(rs.getInt("OBJECT_ID")).toString()));
        familyDebitAccount.setObjectName(rs.getString("NAME"));
        familyDebitAccount.setAmount(rs.getLong("values"));
     //   familyDebitAccount.setOwner((User)rs.getObject("reference"));
        return familyDebitAccount;
    }
}
