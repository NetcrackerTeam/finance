package com.netcracker.controllers;

import com.netcracker.dao.UserDao;
import com.netcracker.models.Status;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.validation.UserValidationRegex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String createUser(
            @RequestParam(value = " name ") String name,
            @RequestParam(value = " eMail ") String eMail,
            @RequestParam(value = " password ") String password,
            Model model) {
        boolean validationUser = userValidation.validateName(name)
                && userValidation.validateEmail(eMail)
                && userValidation.validatePassword(password);
        if (validationUser) {
            User user = new User.Builder()
                    .user_name(name)
                    .user_eMail(eMail)
                    .user_password(password)
                    .userActive(UserStatusActive.YES)
                    .build();
            userDao.createUser(user);
            return "success";
        }

        return "unsuccess";
    }

    @RequestMapping(value = "/updatePassword/{userId}/{userLogin}", method = RequestMethod.POST)
    public String updateUserPassword(
            Model model,
            @PathVariable(value = "userId") String id,
            @RequestParam("newPaswword") String password) {
        logger.debug("updatePasswordByUser in  method updateUserPassword . User id - " + id);
        BigInteger userId = new BigInteger(id);
        boolean validationPassword = userValidation.validatePassword(password);
        if (validationPassword) {
            User user = userDao.getUserById(userId);
            userDao.updateUserPasswordById(user.getId(), password);
            return "success";
        }
        return "unsuccess";

    }

    @RequestMapping(value = "/deactivate", method = RequestMethod.GET)
    @ResponseBody
    public Status deactivateUser(@RequestParam(value = "userId") BigInteger id) {
        logger.debug("updateUserStatus by user id " + id);
        userDao.updateUserStatus(id, UserStatusActive.NO.getId());
        return new Status(true, "Deactivated successfully user " + id);
    }


    @RequestMapping(value = "/updateEmail/{userId}/{userEmail}", method = RequestMethod.POST)
    public String updateEmail(
            Model model,
            @PathVariable(value = "userId") String id,
            @PathVariable("userEmail") String userEmail) {
        logger.debug("updateEmailByUser in  method updateEmail . User id - " + id);
        BigInteger userId = new BigInteger(id);
        boolean validateEmail = userValidation.validateEmail(userEmail);
        if (validateEmail) {
            User user = userDao.getUserById(userId);
            userDao.updateEmail(user.getId(), userEmail);
            return "success";
        }
        return "unsucces";

    }
}
