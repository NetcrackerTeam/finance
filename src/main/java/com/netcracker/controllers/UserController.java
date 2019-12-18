package com.netcracker.controllers;

import com.netcracker.dao.UserDao;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDao userDao;


    private static final Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String CreateUser(
            @RequestParam(value = " name ") String name,
            @RequestParam(value = " eMail ") String eMail,
            @RequestParam(value = " password ") String password,
            Model model) {
        User user = new User.Builder()
                .user_name(name)
                .user_eMail(eMail)
                .user_password(password)
                .userActive(UserStatusActive.YES)
                //.familyDebit()
                //.personalDebit()
                .build();
        userDao.createUser(user);
        return "success";
    }

    @RequestMapping(value = "/updatePassword/{userId}/{userEmail}", method = RequestMethod.POST)
    public String updateUserPassword(
            Model model,
            @PathVariable(value = "userId") String id,
            @PathVariable("userEmail") String email,
            @RequestParam("newPaswword") String password) {
        logger.debug("updatePasswordByUser in  method updateUserPassword . User id - " + id + " login - " + email);
        BigInteger userId = new BigInteger(id);
        User user = userDao.getUserById(userId);
        userDao.updateUserPasswordById(user.getId(), password);
        return "success";
    }


    @RequestMapping(value = "/updateEmail/{userId}/{userEmail}", method = RequestMethod.POST)
    public String updateEmail(
            Model model,
            @PathVariable(value = "userId") String id,
            @PathVariable("userEmail") String userEmail) {
        logger.debug("updateEmailByUser in  method updateEmail . User id - " + id);
        BigInteger userId = new BigInteger(id);
        User user = userDao.getUserById(userId);
        userDao.updateEmail(user.getId(), userEmail);
        return "success";
    }


}
