package com.netcracker.dao.impl;

import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.mapper.PersonalDebitAccountMapper;
import com.netcracker.models.PersonalDebitAccount;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;

public class PersonalDebitAccountDaoImpl  implements PersonalDebitAccountDao {
    private JdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public PersonalDebitAccount getPersonalAccountById(BigInteger id) {
        return this.template.queryForObject(GET_PERSONAL_ACCOUNT_BY_ID, new Object[]{id}, new PersonalDebitAccountMapper());
    }

    @Override
    public void createPersonalAccount(PersonalDebitAccount pda) {
        this.template.update(CREATE_PERSONAL_ACCOUNT, new Object[]{
                pda.getId(),
                pda.getAmount(),
                pda.getOwner()
        });
    }

    @Override
    public void deletePersonalAccountById(BigInteger id) {
        this.template.update(DELETE_USER_FROM_PERSONAL_ACCOUNT,new Object[]{id});
    }

    @Override
    public void deletePersonalAccountByUserId(BigInteger id) {
    }
}
