package com.netcracker.dao.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.mapper.FamilyAccountDebitMapper;
import com.netcracker.dao.impl.mapper.UserDaoMapper;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
                familyDebitAccount.getAmount(),
                familyDebitAccount.getStatus(),
                familyDebitAccount.getOwner()
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
       // User user = this.template.queryForObject(CHEK_USER_ACTIVE_AND_REFERENCED, new Object[]{user_id}, User.class);
        if(new UserDaoImpl().getUserById(user_id).getFamilyDebitAccount() != null){
            logger.debug("Entering check(User_Is_HAS_ACCOUNT_FAMILY=" + account_id + " " + user_id +")");
        } else if (new UserDaoImpl().getUserById(user_id).getName().equals("NO")){
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

    @Override
    public ArrayList<User> getParticipantsOfFamilyAccount(BigInteger debit_id) {
        logger.debug("Entering list(getParticipantsOfFamilyAccount=" + debit_id+ ")");
        return (ArrayList<User>) this.template.query(GET_PARTICIPANTS,  new UserDaoMapper());
    }
}
