package com.netcracker.controllers;

import com.netcracker.dao.UserDao;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.UserService;
import com.netcracker.services.validation.RegexPatterns;
import com.netcracker.services.validation.UserValidationRegex;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

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


    private static final Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping(value = "/updateEmail", method = RequestMethod.POST)
    public String updateEmail(@ModelAttribute
                              @RequestParam Map<String, String> mapUserData, Model model) {
        User userTemp = userDao.getUserByEmail(getCurrentUsername());
        logger.debug(MessageController.LOGGER_UPDATE_EMAIL + userTemp.getId());
        String newEmail = mapUserData.get("newEmail");
        boolean validateEmail = userValidation.validateValueByUser(newEmail, RegexPatterns.EMAIL_PATTERN);
        boolean emailAlreadyExit = (userDao.getNumberOfUsersByEmail(newEmail) > 0);
        if ((validateEmail) && (!emailAlreadyExit)) {
            userDao.updateEmail(userTemp.getId(), newEmail);
            model.addAttribute("successUpdatePass", SUCCESS_UPDATE_EMAIL);
        } else {
            model.addAttribute("errorValidateNewEmail or email already exist", ERROR_VALIDATE_EMAIL);
        }
        return URL.INDEX;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public String updateUserPassword(@ModelAttribute
                                     @RequestParam Map<String, String> mapUserData, Model model) {
        User userTemp = userDao.getUserByEmail(getCurrentUsername());
        logger.debug(MessageController.LOGGER_UPDATE_PASSWORD + userTemp.getId());
        String newPassword = mapUserData.get("newPassword");
        String newConfirmPassword = mapUserData.get("newConfirmPassword");
        boolean confirmPass = userService.confirmUserPassword(newPassword, newConfirmPassword);
        boolean validationPassword = userValidation.validateValueByUser(newPassword, RegexPatterns.PASSWORD_PATTERN);
        if (!confirmPass) {
            model.addAttribute("errorConfirm", INVALID_CONFIRM_PASSWORD);
        } else if (validationPassword) {
            String passwordEncode = userService.encodePassword(newPassword);
            userDao.updateUserPasswordById(userTemp.getId(), passwordEncode);
            model.addAttribute("successUpdatePass", SUCCESS_UPDATE_PASSWORD);
        }
        return URL.INDEX;

    }

    @RequestMapping(value = "/deactivate", method = RequestMethod.GET)
    public String deactivateUser(Model model, Principal principal, HttpServletResponse response, HttpServletRequest request) {
        User userTemp = userDao.getUserByEmail(getCurrentUsername());
        logger.debug("updateUserStatus by user id " + userTemp.getId());
        if (userService.deactivateUser(userTemp)) {
            userDao.updateUserStatus(userTemp.getId(), UserStatusActive.NO.getId());
            familyDebitController.deleteUserFromAccount(userTemp.geteMail(), principal, response, request);
            model.addAttribute("success", MessageController.SUCCESS_DEACTIVATE);
        } else model.addAttribute("unsuccess", UN_SUCCESS_DEACTIVATE);
        return URL.INDEX;
    }

    public static String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

}
