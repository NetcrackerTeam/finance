package com.netcracker.controllers;

import com.netcracker.controllers.validators.UserDataValidator;
import com.netcracker.dao.UserDao;
import com.netcracker.exception.ErrorsMap;
import com.netcracker.exception.UserException;
import com.netcracker.models.Status;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.UserService;
import com.netcracker.services.utils.ExceptionMessages;
import com.netcracker.services.validation.UserValidationRegex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

import static com.netcracker.controllers.MessageController.*;

@Controller
public class UserController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserValidationRegex userValidation;
    @Autowired
    private UserService userService;
    @Autowired
    private FamilyDebitController familyDebitController;
    @Autowired
    private AuthenticationManager authenticationManager;


    private static final Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping(value = "/updateEmail", method = RequestMethod.POST)
    public @ResponseBody Status updateEmail (@RequestBody String email, HttpServletRequest request) {
        User userTemp = userDao.getUserByEmail(getCurrentUsername());
        logger.debug(MessageController.LOGGER_UPDATE_EMAIL + userTemp.getId());

        email = email.replace("\"", "");
        try {
            UserDataValidator.isValidEmail(email);
            if (userDao.getNumberOfUsersByEmail(email) > 0) return showStatusFalse(ExceptionMessages.USER_ALREADY_EXIST);
        } catch (UserException ex) {
            String error = ex.getMessage();
            if (ExceptionMessages.EMPTY_FIELD.equals(error)) return showStatusFalse(ExceptionMessages.EMPTY_FIELD);
            if (ExceptionMessages.INVALID_EMAIL.equals(error)) return showStatusFalse(ExceptionMessages.INVALID_EMAIL);
        }

        userDao.updateEmail(userTemp.getId(), email);
        User newUser = userDao.getUserByEmail(email);

        Authentication newAuth = new  UsernamePasswordAuthenticationToken(newUser.geteMail(), newUser.getPassword(),
                AuthorityUtils.createAuthorityList(newUser.getUserRole().name()));
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return new Status(true, SUCCESS_UPDATE_EMAIL);
    }

    private Status showStatusFalse(String msgKey) {
        return new Status(false, ErrorsMap.getErrorsMap().get(msgKey));
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public @ResponseBody Status updateUserPassword(@RequestBody User user) {
        User userTemp = userDao.getUserByEmail(getCurrentUsername());
        logger.debug(MessageController.LOGGER_UPDATE_PASSWORD + userTemp.getId());

        String oldPassword = userTemp.getPassword();
        String enteredOldPassword = user.getOldPassword();

        try {
            UserDataValidator.isValidPassword(enteredOldPassword);
        } catch (UserException ex) {
            if (ExceptionMessages.EMPTY_FIELD.equals(ex.getMessage())) return showStatusFalse(ExceptionMessages.EMPTY_FIELD);
        }

        if (!BCrypt.checkpw(enteredOldPassword, oldPassword)) return new Status(false, INVALID_OLD_PASSWORD);

        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();

        try {
            UserDataValidator.isValidPassword(password);
            UserDataValidator.comparePasswords(password, confirmPassword);
        } catch (UserException ex) {
            String error = ex.getMessage();
            if (ExceptionMessages.EMPTY_FIELD.equals(error)) return showStatusFalse(ExceptionMessages.EMPTY_FIELD);
            if (ExceptionMessages.LATIN_CHAR.equals(error)) return showStatusFalse(ExceptionMessages.LATIN_CHAR);
            if (ExceptionMessages.PASS_SHORT.equals(error)) return showStatusFalse(ExceptionMessages.PASS_SHORT);
            if (ExceptionMessages.PASS_UPPER.equals(error)) return showStatusFalse(ExceptionMessages.PASS_UPPER);
            if (ExceptionMessages.PASS_LOWER.equals(error)) return showStatusFalse(ExceptionMessages.PASS_LOWER);
            if (ExceptionMessages.PASS_NUM.equals(error)) return showStatusFalse(ExceptionMessages.PASS_NUM);
            if (ExceptionMessages.INVALID_CONFIRM_PASSWORD.equals(error)) return showStatusFalse(ExceptionMessages.INVALID_CONFIRM_PASSWORD);
        }

        String passwordEncode = userService.encodePassword(password);
        userDao.updateUserPasswordById(userTemp.getId(), passwordEncode);
        User newUser = userDao.getUserByEmail(userTemp.geteMail());

        Authentication newAuth = new  UsernamePasswordAuthenticationToken(newUser.geteMail(), newUser.getPassword(),
                AuthorityUtils.createAuthorityList(newUser.getUserRole().name()));
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return new Status(true, SUCCESS_UPDATE_PASSWORD);
    }

    @RequestMapping(value = "/deactivate", method = RequestMethod.GET)
    @ResponseBody
    public Status deactivateUser(Principal principal, HttpServletResponse response, HttpServletRequest request) {
        User userTemp = userDao.getUserByEmail(getCurrentUsername());
        logger.debug("updateUserStatus by user id " + userTemp.getId());

        if (userService.deactivateUser(userTemp)) {
            familyDebitController.deleteUserFromAccount(userTemp.geteMail(), principal, response, request);
            userDao.updateUserStatus(userTemp.getId(), UserStatusActive.NO.getId());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) new SecurityContextLogoutHandler().logout(request, response, auth);
            return new Status();
        }
        return new Status(false, DEACTIVATION_IMPOSSIBLE);
    }

    public static String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

}
