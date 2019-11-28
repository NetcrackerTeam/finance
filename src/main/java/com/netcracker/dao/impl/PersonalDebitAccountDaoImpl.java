package com.netcracker.dao.impl;

import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.impl.mapper.PersonalDebitAccountMapper;
import com.netcracker.models.PersonalDebitAccount;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;

@Component
public class PersonalDebitAccountDaoImpl  implements PersonalDebitAccountDao {
    private static final Logger logger = Logger.getLogger(PersonalDebitAccountDaoImpl.class);
    private JdbcTemplate template;

    @Autowired
    public PersonalDebitAccountDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public PersonalDebitAccount getPersonalAccountById(BigInteger id) {
        logger.info("Entering insert(getPersonalAccountBy=" + id + ")");
        return this.template.queryForObject(GET_PERSONAL_ACCOUNT_BY_ID, new Object[]{new BigDecimal(id)}, new PersonalDebitAccountMapper());
    }

    @Override
    public PersonalDebitAccount createPersonalAccount(PersonalDebitAccount personalDebitAccount) {
        logger.info("Entering insert(createPersonalAccount=" + personalDebitAccount + ")");
        this.template.update(CREATE_PERSONAL_ACCOUNT, new Object[]{
                personalDebitAccount.getObjectName(),
                personalDebitAccount.getAmount(),
                personalDebitAccount.getOwner(),
                personalDebitAccount.getStatus()
        });
        return personalDebitAccount;
    }

    @Override
    public void deletePersonalAccountById(BigInteger id) {
        logger.info("Entering unactive(deletePersonalAccount=" + id + ")");
        this.template.update(DELETE_USER_FROM_PERSONAL_ACCOUNT, id);
    }

    @Override
    public void deletePersonalAccountByUserId(BigInteger id) {
        logger.info("Entering unactive(deletePersonalAccountByUser=" + id + ")");
        if(this.getPersonalAccountById(id).getStatus().equals("NO")){
            assert(false);
        } else {
            template.update(UNACTIVE_USER_FROM_PERSONAL_ACCOUNT, id);
        }
    }
}
