package com.netcracker.controllers;

import com.netcracker.dao.UserDao;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import com.netcracker.services.PersonalDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;

@Controller
public class LoginController {

    @Autowired
    UserDao userDao;

    @Autowired
    PersonalDebitService debitService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return   URL.LOGIN_URL;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return URL.REGISTRATIONS_URL;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute User user, Model model) {
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

}
