package com.netcracker.controllers;

import com.netcracker.dao.UserDao;
import com.netcracker.models.PersonalDebitAccount;
import com.netcracker.models.Status;
import com.netcracker.models.User;
import com.netcracker.services.PersonalDebitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        return "viewsLoginRegestration/layoutLoginUser";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        User user = new User.Builder().build();
        model.addAttribute("user", user);
        return "viewsLoginRegestration/layoutRegistrationUser";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public Status registerUserAccount
            (@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.createUser(user);
        User registered = userDao.getUserByEmail(user.geteMail());
        debitService.createPersonalDebitAccount(new PersonalDebitAccount.Builder()
                .debitOwner(registered)
                .build());
        return new Status(true, "Account was created success");
    }

}
