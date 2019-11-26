package com.netcracker.dao.impl;

import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.impl.mapper.PersonalDebitAccountMapper;
import com.netcracker.models.PersonalDebitAccount;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;

public class PersonalDebitAccountDaoImpl  implements PersonalDebitAccountDao {
    private static final Logger logger = Logger.getLogger(PersonalDebitAccountDaoImpl.class);
    private JdbcTemplate template;

    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public PersonalDebitAccount getPersonalAccountById(BigInteger id) {
        logger.info("Entering insert(getPersonalAccountBy=" + id + ")");
        return this.template.queryForObject(GET_PERSONAL_ACCOUNT_BY_ID, new Object[]{id}, new PersonalDebitAccountMapper());
    }

    @Override
    public void createPersonalAccount(PersonalDebitAccount pda) {
        logger.info("Entering insert(createPersonalAccount=" + pda + ")");
        this.template.update(CREATE_PERSONAL_ACCOUNT, new Object[]{
                pda.getObjectName(),
                pda.getAmount()
        });
    }

    @Override
    public void deletePersonalAccountById(BigInteger id) {
        logger.info("Entering unactive(deletePersonalAccount=" + id + ")");
        this.template.update(UNACTIVE_USER_FROM_PERSONAL_ACCOUNT,new Object[]{id});
    }

    @Override
    public void deletePersonalAccountByUserId(BigInteger id) {
        logger.info("Entering unactive(deletePersonalAccountByUser=" + id + ")");

        BigInteger reference =  template.queryForObject(
                DELETE_USER_FROM_PERSONAL_ACCOUNT, new Object[]{id}, BigInteger.class);
        if(reference == null){
            assert(false);
        } else {
            deletePersonalAccountById(reference);
        }
    }
}
