package com.netcracker.dao.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.impl.mapper.FamilyAccountDebitMapper;
import com.netcracker.models.FamilyDebitAccount;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;

@Component
public class FamilyAccountDebitDaoImpl implements FamilyAccountDebitDao {

    private static final Logger logger = Logger.getLogger(FamilyAccountDebitDaoImpl.class);
    private JdbcTemplate template;

    @Autowired
    public FamilyAccountDebitDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public FamilyDebitAccount getFamilyAccountById(BigInteger id) {
        logger.debug("Entering insert(getFamilyAccountById=" + id + ")");
        return this.template.queryForObject(FIND_FAMILY_ACCOUNT_BY_ID, new Object[]{new BigDecimal(id)}, new FamilyAccountDebitMapper());
    }

    @Override
    public FamilyDebitAccount createFamilyAccount(FamilyDebitAccount familyDebitAccount) {
        logger.debug("Entering insert(FamilyDebitAccount=" + familyDebitAccount + ")");
        this.template.update(ADD_NEW_FAMILY_ACCOUNT, new Object[]{
                familyDebitAccount.getObjectName(),
                familyDebitAccount.getAmount()
        });
        return familyDebitAccount;
    }

    @Override
    public void deleteFamilyAccount(BigInteger id) {
        logger.debug("Entering unactive(deleteFamilyAccount=" + id + ")");
        this.template.update(SET_FAMILY_ACCOUNT_UNACTIVE, new Object[]{id});
    }

    @Override
    public void addUserToAccountById(BigInteger account_id, BigInteger user_id) {
        logger.debug("Entering insert(addUserToAccountById=" + account_id + " " + user_id + ")");
        String list_value = this.template.queryForObject(CHEK_USER_ACTIVE, new Object[]{user_id}, String.class);
        BigInteger reference = this.template.queryForObject(CHEK_FAMILY_ACC, new Object[]{account_id, user_id}, BigInteger.class);

        if(reference != null){
            logger.debug("Entering check(User_Is_HAS_ACCOUNT_FAMILY=" + account_id + " " + user_id +")");
        } else if (list_value.equals("NO")){
            logger.debug("Entering check(User_Is_UnActive=" + user_id + ")");
        } else{
            this.template.update(ADD_USER_BY_ID, new Object[]{
                    account_id,
                    user_id
            });
        }
    }

    @Override
    public void deleteUserFromAccountById(BigInteger account_id, BigInteger user_id) {
        logger.debug("Entering unactive(deleteUserFromAccountById=" + account_id + " " + user_id + ")");
        this.template.update(DELETE_USER_FROM_FAMILY_ACCOUNT, new Object[]{
                account_id,
                user_id
        });
    }
}
