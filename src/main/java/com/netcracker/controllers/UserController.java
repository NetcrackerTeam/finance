package com.netcracker.controllers;

import com.netcracker.dao.UserDao;
import com.netcracker.models.Status;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.validation.RegexPatterns;
import com.netcracker.services.validation.UserValidationRegex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserValidationRegex userValidation;


    private static final Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping(value = "/updatePassword/{userId}/{userLogin}", method = RequestMethod.POST)
    public Status updateUserPassword(
            @PathVariable(value = "userId") String id,
            @RequestParam("newPaswword") String password) {
        logger.debug("updatePasswordByUser in  method updateUserPassword . User id - " + id);
        BigInteger userId = new BigInteger(id);
        boolean validationPassword = userValidation.validateValueByUser(password, RegexPatterns.PASSWORD_PATTERN);
        if (validationPassword) {
            User user = userDao.getUserById(userId);
            userDao.updateUserPasswordById(user.getId(), password);
        }
        return new Status (true, "Password was update ");

    }

    @RequestMapping(value = "/deactivate", method = RequestMethod.GET)
    @ResponseBody
    public Status deactivateUser(@RequestParam(value = "userId") BigInteger id) {
        logger.debug("updateUserStatus by user id " + id);
        userDao.updateUserStatus(id, UserStatusActive.NO.getId());
        return new Status(true, "Deactivated successfully user " + id);
    }


    @RequestMapping(value = "/updateEmail/{userId}/{userEmail}", method = RequestMethod.POST)
    public Status updateEmail(
            @PathVariable(value = "userId") String id,
            @PathVariable("userEmail") String userEmail) {
        logger.debug("updateEmailByUser in  method updateEmail . User id - " + id);
        BigInteger userId = new BigInteger(id);
        boolean validateEmail = userValidation.validateValueByUser(userEmail,RegexPatterns.EMAIL_PATTERN);
        if (validateEmail) {
            User user = userDao.getUserById(userId);
            userDao.updateEmail(user.getId(), userEmail);
        }
            return  new Status(true, "Email was update ");
    }
}
