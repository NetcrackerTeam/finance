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

@Controller
public class UserIdController {
    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/getUserDebitId", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public ResponseEntity<BigInteger> getUserDebitId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) email = ((UserDetails)principal).getUsername();
        else email = principal.toString();
        User user = userDao.getUserByEmail(email);
        BigInteger debitId = user.getPersonalDebitAccount();
        return new ResponseEntity<>(debitId, HttpStatus.OK);
    }
}
