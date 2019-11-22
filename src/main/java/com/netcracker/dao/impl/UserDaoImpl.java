package com.netcracker.dao.impl;

import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.mapper.UserDaoMapper;
import com.netcracker.models.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private JdbcTemplate template;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }


    @Override
    public User createUser(User user) {
        logger.info("Entering insert(user=" + user + ")");
        this.template.update(CREATE_USER, new Object[]{
                user.getName(),
                user.geteMail(),
                user.getPassword(),
                user.getFamilyDebitAccount(),
                user.getPersonalDebitAccount()

        });
        return user;
    }

    @Override
    public User getUserById(BigInteger id) {
        logger.info("Entering getUserByUserId(" + id + ")");
        return template.queryForObject(GET_USER_BY_USER_ID, new Object[]{id},
                new UserDaoMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        return null;
    }

    @Override
    public void updateUserPasswordById(BigInteger id, String newPassword) {

    }

    @Override
    public Collection<User> getAllUsersByFamilyAccountId(List<BigInteger> userId, BigInteger familyId) {
        return null;
    }

    @Override
    public User getUserByFamilyAccountId(BigInteger id, BigInteger familyId) {
        return null;
    }
}
