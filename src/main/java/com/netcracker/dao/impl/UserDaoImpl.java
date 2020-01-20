package com.netcracker.dao.impl;

import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.mapper.UserDaoMapper;
import com.netcracker.exception.UserException;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserRole;
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

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public User createUser(User user) {
        logger.debug("Entering insert(user=" + user + ")");
        this.template.update(CREATE_USER,
                user.geteMail(),
                user.getPassword(),
                user.getName(),
                user.getUserStatusActive().getId().toString());
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

    @Override
    public void updateRole(BigInteger id, BigInteger roleId) {
        logger.debug(
                "Entering updateRole(id=" + id + "," + " role=" + roleId
                        + ")");
        template.update(UPDATE_ROLE, roleId, (id));
    }

    @Override
    public int getNumberOfUsersByEmail(String email) {
        return template.queryForObject(USER_EXIST, new Object[]{email}, Integer.class);
    }

    @Override
    public User getParticipantByEmail(String login) {
        try {
            logger.debug("Entering getUserByUserLogin(login=" + login + ")");
            return template.queryForObject(GET_FAMILY_ACCOUNT_OF_PARTICIPANT_BY_LOGIN, new Object[]{login},
                    new UserDaoMapper());
        } catch (
                EmptyResultDataAccessException EmptyResultDataAccessException) {
            throw new UserException(ExceptionMessages.ERROR_MESSAGE_USER);
        }
    }

    @Override
    public void setPasswordToken(String email, String token) {
        int result = template.queryForObject(IF_TOKEN_ATTR_EXISTS, new Object[]{email}, Integer.class);
        BigInteger id = getUserByEmail(email).getId();
        if (result == 0)
            template.update(CREATE_USER_TOKEN, id, token);
        else
            template.update(UPDATE_TOKEN, token, id);
    }

    @Override
    public User findUserByToken(String token) {
        return template.queryForObject(GET_USER_BY_TOKEN, new Object[]{token}, new UserDaoMapper());
    }

    @Override
    public void clearPassToken(BigInteger userId) {
        template.update(CLEAR_PASS_TOKEN, userId);
    }
}
