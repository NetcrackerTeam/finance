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
                personalDebitAccount.getAmount().toString(),
                personalDebitAccount.getStatus().getId().toString(),
                personalDebitAccount.getOwner().getId().toString()
        });
        return personalDebitAccount;
    }

    @Override
    public void deletePersonalAccountById(BigInteger account_id, BigInteger user_id) {
        logger.info("Entering unactive(deletePersonalAccount=" + account_id + ")");
        this.template.update(DELETE_USER_FROM_PERSONAL_ACCOUNT, new Object[]{
                account_id.toString(),
                user_id.toString()
        });
    }

    @Override
    public void deletePersonalAccountByUserId(BigInteger account_id) {
        logger.info("Entering unactive(deletePersonalAccountByUser=" + account_id + ")");
        this.template.update(UNACTIVE_USER_FROM_PERSONAL_ACCOUNT, new Object[]{
                account_id.toString(),
        });
    }
}
