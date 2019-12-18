package com.netcracker.dao.impl;

import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.mapper.UserDaoMapper;
import com.netcracker.exception.UserException;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.utils.ExceptionMessages;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;

@Repository
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
        try {
            logger.debug("Entering getUserByUserId(" + id + ")");
            return template.queryForObject(GET_USER_BY_USER_ID, new Object[]{id},
                    new UserDaoMapper());
        } catch (
                EmptyResultDataAccessException EmptyResultDataAccessException) {
            throw new UserException(ExceptionMessages.ERROR_MESSAGE_USER);
        }

    }

    @Override
    public User getUserByEmail(String login) {
        try {
            logger.debug("Entering getUserByUserLogin(login=" + login + ")");
            return template.queryForObject(GET_USER_BY_LOGIN, new Object[]{login},
                    new UserDaoMapper());
        } catch (
                EmptyResultDataAccessException EmptyResultDataAccessException) {
            throw new UserException(ExceptionMessages.ERROR_MESSAGE_USER);
        }

    }

    @Override
    public void updateUserPasswordById(BigInteger id, String newPassword) {
        logger.debug(
                "Entering updatePassword(id=" + id + "," + " password=" + newPassword
                        + ")");
        template.update(UPDATE_PASSWORD, newPassword, (id));
    }

    @Override
    public void updateUserStatus(BigInteger id, BigInteger newStatus) {
        logger.debug(
                "Entering updateStatus(id=" + id + "," + " status=" + newStatus
                        + ")");
        template.update(UPDATE_STATUS, newStatus, (id));
    }


    @Override
    public void updateEmail(BigInteger id, String newEmail) {
        logger.debug(
                "Entering updateEmail(id=" + id + "," + " newEmail=" + newEmail
                        + ")");
        template.update(UPDATE_EMAIL, newEmail, (id));
    }
}
