package com.netcracker.dao.impl.mapper;


import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.User;
import com.netcracker.models.enums.PersonalAccountStatusActive;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalDebitAccountMapper implements RowMapper<PersonalDebitAccount> {
    @Override
    public PersonalDebitAccount mapRow(ResultSet rs, int i) throws SQLException {

        UserDaoMapper userMapper = new UserDaoMapper();
        User user = userMapper.mapRow(rs, i);

        return new PersonalDebitAccount.Builder()
                .debitId(rs.getBigDecimal("PER_DEB_ACC1").toBigInteger())
                .debitObjectName(rs.getString("NAME_PERSONAL_DEBIT"))
                .debitAmount(rs.getDouble("AMOUNT_PERSONAL_DEBIT"))
                .debitPersonalAccountStatus(PersonalAccountStatusActive.getStatusByKey(rs.getBigDecimal("STATUS_PERSONAL_DEBIT").toBigInteger()))
                .debitOwner(user)
                .build();
    }
}
