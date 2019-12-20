package com.netcracker.services.impl;

import com.netcracker.services.validation.UserValidationRegex;
import org.junit.Assert;
import org.junit.Test;

public class ValidationRegexTest {
    private UserValidationRegex userValidationRegex = new UserValidationRegex();


    @Test
    public void ValidPasswordTest() {
        String[] passwordValid = {"mkYOn12$", " dkKdw12#@"};
        for (String temp : passwordValid) {
            boolean valid = userValidationRegex.validatePassword(temp);
            Assert.assertEquals(true, valid);
        }
    }

    @Test
    public void InValidPasswordTest() {
        String[] passwordInValid = {"mn12$", " dkkeww12#@"};
        for (String temp : passwordInValid) {
            boolean valid = userValidationRegex.validatePassword(temp);
            Assert.assertEquals(false, valid);
        }
    }

    @Test
    public void ValidEmail(){
        String [] emailValid = {"admin@gmail.com", "genygoy@gmail.com"};
        for (String temp : emailValid) {
            boolean valid =  userValidationRegex.validateEmail(temp);
            Assert.assertEquals(true, valid);
        }
    }


    @Test
    public void InValidEmail(){
        String [] emailValid = {"@admingmail.com", "genygogmail.com"};
        for (String temp : emailValid) {
            boolean valid =  userValidationRegex.validateEmail(temp);
            Assert.assertEquals(false, valid);
        }
    }

    @Test
    public void InValidName(){
        String [] nameInvalid = {"123Gejhfd", "trey12"};
        for (String temp : nameInvalid) {
            boolean valid =  userValidationRegex.validateName(temp);
            Assert.assertEquals(false, valid);
        }
    }


    @Test
    public void ValidName(){
        String [] nameValid = {"Eugene Goya", "Eugene Mak"};
        for (String temp : nameValid) {
            boolean valid =  userValidationRegex.validateName(temp);
            Assert.assertEquals(true, valid);
        }
    }



}
