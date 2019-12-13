package com.netcracker.services.impl;

import com.netcracker.configs.WebConfig;
import com.netcracker.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void isUserActiveTest() {
        boolean expectedActive = userService.isUserActive(BigInteger.valueOf(24));
        assertEquals(true, expectedActive);
    }


    @Test
    public void isUserHasFamilyAccountTest() {
        boolean expected = userService.isUserHaveFamilyAccount(BigInteger.valueOf(24));
        assertEquals(true, expected);
    }
}
