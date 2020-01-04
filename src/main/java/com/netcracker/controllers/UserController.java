package com.netcracker.controllers;

import com.netcracker.dao.UserDao;
import com.netcracker.models.Status;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.validation.RegexPatterns;
import com.netcracker.services.validation.UserValidationRegex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserValidationRegex userValidation;


    private static final Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping(value = "/updateEmail", method = RequestMethod.POST)
    public String updateEmail(@ModelAttribute
                                  @RequestParam Map<String, String> mapUserData, Model model ) {
        User userTemp = userDao.getUserByEmail(getCurrentUsername());
        logger.debug("updateEmailByUser in  method updateEmail . User id - " + userTemp.getId());
        String oldEmail = mapUserData.get("oldEmail");
        String newEmail = mapUserData.get("newEmail");
        boolean validOldEmail = userTemp.geteMail().equals(oldEmail);
        boolean validateEmail = userValidation.validateValueByUser(newEmail,RegexPatterns.EMAIL_PATTERN);
        if (!validOldEmail){
            model.addAttribute("error", "_________________________");
        }else
        if (validateEmail) {
            userDao.updateEmail(userTemp.getId(),newEmail);
        }else {
            model.addAttribute("error", "Username or password is incorrect.");
        }
        return "index";
    }

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

    public  static  String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

}
