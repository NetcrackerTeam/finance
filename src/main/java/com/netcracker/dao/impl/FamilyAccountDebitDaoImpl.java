package com.netcracker.dao.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.mappers.FamilyAccountDebitMapper;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.User;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;

public class FamilyAccountDebitDaoImpl extends FamilyDebitAccount implements FamilyAccountDebitDao {

    private JdbcTemplate template;

    private static final String ADD_USER_BY_ID = "INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,?,?)";
    private static final String FIND_FAMILY_ACCOUNT_BY_ID = "SELECT NAME FROM OBJECTS WHERE OBJECT_ID = ?";
    private static final String ADD_NEW_FAMILY_ACCOUNT = "INSERT INTO OBJECTS(OBJECT_ID,OBJECT_TYPE_ID,NAME) VALUES (objects_id_s.nextval, ?, ?)";
    private static final String DELETE_USER_FROM_FAMILY_ACCOUNT = "DELETE FROM OBJREFERENCE WHERE ATTR_ID = 8, OBJECT_ID = ?, REFERENCE = ?";
    private static final String ADD_AMOUNT = "insert into attributes(attr_id, object_id, values) values(9, ?, ?);";


    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public FamilyDebitAccount getFamilyAccountById(BigInteger id) {
        return this.template.queryForObject(FIND_FAMILY_ACCOUNT_BY_ID, new Object[]{id}, new FamilyAccountDebitMapper());
    }

    @Override
    public void createFamilyAccount(Integer amount) {
        this.template.update(ADD_NEW_FAMILY_ACCOUNT, new Object[]{
                super.getObjectTypeId(),
                super.getObjectName()
        });
        this.template.update(ADD_AMOUNT, new Object[]{
                super.getObjectTypeId(),
                amount
        });

    }

    @Override
    public void deleteFamilyAccount(BigInteger id) {

    }

    @Override
    public void addUserToAccountById(User user) {
        this.template.update(ADD_USER_BY_ID, new Object[]{
                this.getId(),
                user.getId()
        });
    }

    @Override
    public void deleteUserFromAccountById(User user) {
        this.template.update(DELETE_USER_FROM_FAMILY_ACCOUNT, new Object[]{
                this.getId(),
                user.getId()
        });
    }
}
