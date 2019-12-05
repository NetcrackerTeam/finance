package com.netcracker.services.validation;
import com.netcracker.dao.UserDao;
import com.netcracker.dao.impl.mapper.UserDaoMapper;
import com.netcracker.models.User;
import com.netcracker.services.validation.errorMessage.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation extends AbstractValidation {

    @Autowired
    private UserDao userDao;

    private JdbcTemplate template;
//    private static final String SQL_NAME = "select name from objects where name = ?";

    @Autowired
    public UserValidation(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }


    public Map<String, String> validateInputId(String id) {
        validateId(id);
        return getErrorMapMessage();
    }

    public void ValidName(String name) throws SQLException {
//        User user = userDao.getUserByLogin(name);
//        PreparedStatement ptmt =  (PreparedStatement) template.queryForList(SQL_NAME, name);
//        ptmt.setString(1,name);
//        ptmt.executeQuery(SQL_NAME);
        PreparedStatement ptmt = (PreparedStatement) template.getDataSource();
        ptmt.setString(1, name);
    }

    public Map<String,String> validationEmail(String email){
        if (!checkEmail(email)){
            setErrorToMapMessage("WRONG_EMAIL_ERROR",
                    ErrorMessages.WRONG_EMAIL_FORMAT);
        }
        return getErrorMapMessage();
    }

    public Map<String, String >  validatePassword(String password) {
        if (!checkPassword(password)) {
            setErrorToMapMessage("PASSWORD ERROR FORMAT",
                    ErrorMessages.PASSWORD_ERROR);
        }
        return getErrorMapMessage();
    }

    private boolean checkEmail(String name) {

        Pattern p = Pattern.compile(RegexPatterns.EMAIL_PATTERN);
        Matcher m = p.matcher(name);
        return m.matches();
    }

    private boolean checkPassword(String name) {
        Pattern p = Pattern.compile(RegexPatterns.PASSWORD_PATTERN);
        Matcher m = p.matcher(name);
        return m.matches();
    }
}