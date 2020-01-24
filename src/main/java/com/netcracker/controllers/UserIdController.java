package com.netcracker.controllers;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.UserDao;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;
import java.security.Principal;

@Controller
public class UserIdController {
    @Autowired
    UserDao userDao;
    @Autowired
    FamilyAccountDebitDao familyAccountDebitDao;
    private User user;
    private BigInteger personalDebitId;
    private BigInteger familyDebitId;
    private String isCheckedIndex = "false";

    @RequestMapping(value = "/getUserDebitId", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public ResponseEntity<BigInteger> getUserDebitId() {
        getUserInfo();
        return new ResponseEntity<>(personalDebitId, HttpStatus.OK);
    }

    @RequestMapping(value = "/getFamilyDebitId", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public ResponseEntity<BigInteger> getFamilyDebitId() {
        getParticipantInfo();
        return new ResponseEntity<>(familyDebitId, HttpStatus.OK);
    }

    @RequestMapping(value = "/getUserInfo", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public ResponseEntity<User> getUser() {
        getUserInfo();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/getUserId", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public ResponseEntity<BigInteger> getUserId() {
        getParticipantInfo();
        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getUserRole", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public ResponseEntity<UserRole> getUserRole() {
        getParticipantInfo();
        return new ResponseEntity<>(user.getUserRole(), HttpStatus.OK);
    }

    @RequestMapping(value = "/templateURL", method = RequestMethod.GET)
    public String templateMethod() {
        return URL.TEMPLATE_URL;
    }

    @RequestMapping(value = "/sendCheckedIndex", method = RequestMethod.POST)
    public ResponseEntity<String> sendCreditId(@RequestBody String isCheckedIndex) {
        this.isCheckedIndex = isCheckedIndex;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/getCheckedIndex", method = RequestMethod.GET)
    public ResponseEntity<String> getCheckedIndex() {
        return new ResponseEntity<>(isCheckedIndex, HttpStatus.OK);
    }

    private void getUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) email = ((UserDetails)principal).getUsername();
        else email = principal.toString();
        user = userDao.getUserByEmail(email);
        personalDebitId = user.getPersonalDebitAccount();
    }

    private void getParticipantInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) email = ((UserDetails)principal).getUsername();
        else email = principal.toString();
        user = userDao.getParticipantByEmail(email);
        familyDebitId = user.getFamilyDebitAccount();
    }

    public BigInteger getAccountByPrincipal(Principal principal) {
        User user = userDao.getUserByEmail(principal.getName());
        return user.getPersonalDebitAccount();
    }
}
