package com.netcracker.dao.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.impl.mapper.FamilyAccountDebitMapper;
import com.netcracker.models.FamilyDebitAccount;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;

public class FamilyAccountDebitDaoImpl implements FamilyAccountDebitDao {

    private JdbcTemplate template;


    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public FamilyDebitAccount getFamilyAccountById(BigInteger id) {
        return this.template.queryForObject(FIND_FAMILY_ACCOUNT_BY_ID, new Object[]{id}, new FamilyAccountDebitMapper());
    }

    @Override
    public FamilyDebitAccount createFamilyAccount(FamilyDebitAccount familyDebitAccount) {
        this.template.update(ADD_NEW_FAMILY_ACCOUNT, new Object[]{
                familyDebitAccount.getObjectName(),
                familyDebitAccount.getAmount()
        });
        return familyDebitAccount;
    }

    @Override
    public void deleteFamilyAccount(BigInteger id) {
        this.template.update(SET_FAMILY_ACCOUNT_UNACTIVE, new Object[]{id});
    }

    @Override
    public void addUserToAccountById(BigInteger account_id, BigInteger user_id) {
        this.template.update(ADD_USER_BY_ID, new Object[]{
                account_id,
                user_id
        });
    }

    @Override
    public void deleteUserFromAccountById(BigInteger account_id, BigInteger user_id) {
        this.template.update(DELETE_USER_FROM_FAMILY_ACCOUNT, new Object[]{
                account_id,
                user_id
        });
    }
}
