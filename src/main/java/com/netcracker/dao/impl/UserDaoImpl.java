package com.netcracker.dao.impl;

import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.mapper.UserDaoMapper;
import com.netcracker.models.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
public class UserDaoImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private JdbcTemplate template;

    public UserDaoImpl(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }


    @Override
    public User createUser(User user) {
        logger.debug("Entering insert(user=" + user + ")");
        this.template.update(CREATE_USER, new Object[]{
                user.geteMail(),
                user.getPassword(),
                user.getName(),
                user.getUserStatusActive().getId().toString()

        });
        return user;
    }

    @Override
    public User getUserById(BigInteger id) {
        logger.debug("Entering getUserByUserId(" + id + ")");
        return template.queryForObject(GET_USER_BY_USER_ID, new Object[]{new BigDecimal(id)},
                new UserDaoMapper());
    }

    @Override
    public User getUserByLogin(String login) {
        logger.debug("Entering getUserByUserLogin(login=" + login + ")");
        return template.queryForObject(GET_USER_BY_LOGIN, new Object[]{login},
                new UserDaoMapper());
    }

    @Override
    public void updateUserPasswordById(BigInteger id, String newPassword) {
        logger.debug(
                "Entering updatePassword(id=" + id + "," + " password=" + newPassword
                        + ")");
        template.update(UPDATE_PASSWORD, newPassword, new BigDecimal(id));
    }


}
