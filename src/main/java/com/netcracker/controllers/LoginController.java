package com.netcracker.controllers;

import com.netcracker.controllers.validators.UserDataValidator;
import com.netcracker.dao.UserDao;
import com.netcracker.exception.ErrorsMap;
import com.netcracker.exception.UserException;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.JobService;
import com.netcracker.services.PersonalDebitService;
import com.netcracker.services.impl.JobServiceImpl;
import com.netcracker.services.utils.ExceptionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PersonalDebitService debitService;

    @Autowired
    private PasswordEncoder passwordEncoder;


//    @Autowired
//    private JobService jobService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error) {
        //for local test
        //  jobService.executeRemindAutoIncomePersonalJob();
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }
        return URL.LOGIN_URL;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return URL.REGISTRATIONS_URL;
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public String showForgotPass() {
        return URL.FORGOT_PASS_URL;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute User user, Model model) {
        try {
            String username = user.getName();
            String password = user.getPassword();
            String email = user.geteMail();
            String confirmPassword = user.getConfirmPassword();

            UserDataValidator.isValidEmail(email);
            UserDataValidator.isValidUsername(username);
            UserDataValidator.isValidPassword(password);
            UserDataValidator.comparePasswords(password, confirmPassword);

            if (userDao.getNumberOfUsersByEmail(email) > 0) return showMsg(model, ExceptionMessages.USER_ALREADY_EXIST);
        } catch (UserException ex) {
            String error = ex.getMessage();
            if (ExceptionMessages.EMPTY_FIELD.equals(error)) return showMsg(model, ExceptionMessages.EMPTY_FIELD);
            if (ExceptionMessages.INVALID_EMAIL.equals(error)) return showMsg(model, ExceptionMessages.INVALID_EMAIL);
            if (ExceptionMessages.LATIN_LETTERS.equals(error)) return showMsg(model, ExceptionMessages.LATIN_LETTERS);
            if (ExceptionMessages.NAME_SHORT.equals(error)) return showMsg(model, ExceptionMessages.NAME_SHORT);
            if (ExceptionMessages.LATIN_CHAR.equals(error)) return showMsg(model, ExceptionMessages.LATIN_CHAR);
            if (ExceptionMessages.PASS_SHORT.equals(error)) return showMsg(model, ExceptionMessages.PASS_SHORT);
            if (ExceptionMessages.PASS_UPPER.equals(error)) return showMsg(model, ExceptionMessages.PASS_UPPER);
            if (ExceptionMessages.PASS_LOWER.equals(error)) return showMsg(model, ExceptionMessages.PASS_LOWER);
            if (ExceptionMessages.PASS_NUM.equals(error)) return showMsg(model, ExceptionMessages.PASS_NUM);
            if (ExceptionMessages.INVALID_CONFIRM_PASSWORD.equals(error)) return showMsg(model, ExceptionMessages.INVALID_CONFIRM_PASSWORD);
        }

        model.addAttribute("user", user);
        user.setUserStatusActive(UserStatusActive.YES);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.createUser(user);
        User registered = userDao.getUserByEmail(user.geteMail());
        debitService.createPersonalDebitAccount(new PersonalDebitAccount.Builder()
                .debitOwner(registered)
                .build());
        return URL.LOGIN_URL;
    }

    private String showMsg(Model model, String msgKey) {
        model.addAttribute("message", ErrorsMap.getErrorsMap().get(msgKey));
        return URL.REGISTRATIONS_URL;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return URL.LOGIN_URL;
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public String forgotPass() {
        return URL.LOGIN_URL;
    }

}
