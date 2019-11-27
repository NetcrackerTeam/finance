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
                .debitId(rs.getBigDecimal("personal_id").toBigInteger())
                .debitObjectName(rs.getString("name_personal_debit"))
                .debitAmount(Long.valueOf(rs.getString("amount_personal_debit")))
                .debitPersonalAccountStatus(PersonalAccountStatusActive.getStatusByKey(rs.getBigDecimal("status_personal_debit").toBigInteger()))
                .debitOwner(user).build();
    }
}
