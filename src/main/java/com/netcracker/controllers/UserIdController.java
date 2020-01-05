package com.netcracker.controllers;

import com.netcracker.dao.UserDao;
import com.netcracker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.math.BigInteger;
import java.security.Principal;

@Controller
public class UserIdController {
    @Autowired
    UserDao userDao;
    private User user;
    private BigInteger personalDebitId;

    @RequestMapping(value = "/getUserDebitId", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public ResponseEntity<BigInteger> getUserDebitId() {
        getUserInfo();
        return new ResponseEntity<>(personalDebitId, HttpStatus.OK);
    }

    @RequestMapping(value = "/getFamilyDebitId", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public ResponseEntity<BigInteger> getFamilyDebitId() {
        getUserInfo();
        BigInteger familyDebitId = user.getFamilyDebitAccount();
        return new ResponseEntity<>(familyDebitId, HttpStatus.OK);
    }

    @RequestMapping(value = "/templateURL", method = RequestMethod.GET)
    public String templateMethod() {
        return URL.TEMPLATE_URL;
    }

    private void getUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) email = ((UserDetails)principal).getUsername();
        else email = principal.toString();
        user = userDao.getUserByEmail(email);
        personalDebitId = user.getPersonalDebitAccount();
    }

    public BigInteger getAccountByPrincipal(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());
        return user.getPersonalDebitAccount();
    }
}
