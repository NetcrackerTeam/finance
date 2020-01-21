package com.netcracker.controllers;

import com.netcracker.controllers.validators.UserDataValidator;
import com.netcracker.dao.UserDao;
import com.netcracker.exception.ErrorsMap;
import com.netcracker.exception.UserException;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.EmailServiceSender;
import com.netcracker.services.JobService;
import com.netcracker.services.PersonalDebitService;
import com.netcracker.services.UserService;
import com.netcracker.services.impl.JobServiceImpl;
import com.netcracker.services.utils.ExceptionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.math.BigInteger;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PersonalDebitService debitService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    EmailServiceSender emailServiceSender;

    @Autowired
    UserService userService;


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
        String pageURL = URL.REGISTRATIONS_URL;
        try {
            String username = user.getName();
            String password = user.getPassword();
            String email = user.geteMail();
            String confirmPassword = user.getConfirmPassword();

            UserDataValidator.isValidEmail(email);
            UserDataValidator.isValidUsername(username);
            UserDataValidator.isValidPassword(password);
            UserDataValidator.comparePasswords(password, confirmPassword);

            if (userDao.getNumberOfUsersByEmail(email) > 0) return showMsg(model, ExceptionMessages.USER_ALREADY_EXIST, pageURL);
        } catch (UserException ex) {
            String error = ex.getMessage();
            if (ExceptionMessages.EMPTY_FIELD.equals(error)) return showMsg(model, ExceptionMessages.EMPTY_FIELD, pageURL);
            if (ExceptionMessages.INVALID_EMAIL.equals(error)) return showMsg(model, ExceptionMessages.INVALID_EMAIL, pageURL);
            if (ExceptionMessages.LATIN_LETTERS.equals(error)) return showMsg(model, ExceptionMessages.LATIN_LETTERS, pageURL);
            if (ExceptionMessages.NAME_SHORT.equals(error)) return showMsg(model, ExceptionMessages.NAME_SHORT, pageURL);
            return showPasswordMessages(model, error, pageURL);
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

    private String showMsg(Model model, String msgKey, String pageURL) {
        model.addAttribute("message", ErrorsMap.getErrorsMap().get(msgKey));
        return pageURL;
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
    public String forgotPass(@RequestParam String email, Model model, HttpServletRequest request) {
        try {
            UserDataValidator.isValidEmail(email);
            if (userDao.getNumberOfUsersByEmail(email) == 0) return showMsg(model, ExceptionMessages.USER_NOT_EXIST, URL.FORGOT_PASS_URL);
        } catch (UserException ex) {
            String error = ex.getMessage();
            if (ExceptionMessages.EMPTY_FIELD.equals(error)) return showMsg(model, ExceptionMessages.EMPTY_FIELD, URL.FORGOT_PASS_URL);
            if (ExceptionMessages.INVALID_EMAIL.equals(error)) return showMsg(model, ExceptionMessages.INVALID_EMAIL, URL.FORGOT_PASS_URL);
        }

        String token = UUID.randomUUID().toString();
        User user = userDao.getUserByEmail(email);
        userDao.setPasswordToken(email, token);
        emailServiceSender.sendMailResetPass(email, user.getName(), token,
                request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath()), user.getId());
        model.addAttribute("message", "Check your mail");
        return URL.FORGOT_PASS_URL;
    }

    @RequestMapping(value = "/resetPass", method = RequestMethod.GET)
    public String resetPass(Model model, @RequestParam("id") BigInteger id, @RequestParam("token") String token) {
        model.addAttribute("user", new User());
        userService.validatePasswordResetToken(id, token);
        return URL.RESET_PASS;
    }

    @RequestMapping(value = "/savePass", method = RequestMethod.POST)
    public String savePassword(HttpServletRequest request, HttpServletResponse response,
                               Model model, @ModelAttribute User modelUser) {
        String pageURL = URL.RESET_PASS;
        String password = modelUser.getPassword();
        String confirmPassword = modelUser.getConfirmPassword();

        try {
            UserDataValidator.isValidPassword(password);
            UserDataValidator.comparePasswords(password, confirmPassword);
        } catch (UserException ex) {
            String error = ex.getMessage();
            if (ExceptionMessages.EMPTY_FIELD.equals(error)) return showMsg(model, ExceptionMessages.EMPTY_FIELD, pageURL);
            return showPasswordMessages(model, error, pageURL);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userDao.updateUserPasswordById(user.getId(), new BCryptPasswordEncoder().encode(password));
        userDao.clearPassToken(user.getId());
        return logoutPage(request, response);
    }

    private String showPasswordMessages(Model model, String error, String pageURL) {
        if (ExceptionMessages.LATIN_CHAR.equals(error)) return showMsg(model, ExceptionMessages.LATIN_CHAR, pageURL);
        if (ExceptionMessages.PASS_SHORT.equals(error)) return showMsg(model, ExceptionMessages.PASS_SHORT, pageURL);
        if (ExceptionMessages.PASS_UPPER.equals(error)) return showMsg(model, ExceptionMessages.PASS_UPPER, pageURL);
        if (ExceptionMessages.PASS_LOWER.equals(error)) return showMsg(model, ExceptionMessages.PASS_LOWER, pageURL);
        if (ExceptionMessages.PASS_NUM.equals(error)) return showMsg(model, ExceptionMessages.PASS_NUM, pageURL);
        if (ExceptionMessages.INVALID_CONFIRM_PASSWORD.equals(error)) return showMsg(model, ExceptionMessages.INVALID_CONFIRM_PASSWORD, pageURL);
        return showMsg(model, ExceptionMessages.UNKNOWN_ERR, pageURL);
    }

}
