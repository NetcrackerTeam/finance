package com.netcracker.services.impl;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.services.FamilyDebitService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

public class FamilyDebitAccountServiceTest {

    @Mock
    private FamilyAccountDebitDao familyAccountDebitDao;

    @InjectMocks
    private FamilyDebitServiceImpl familyDebitService;

    private static final BigInteger id = BigInteger.valueOf(3);


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getFamilyAccount(){
        FamilyDebitAccount familyDebitAccount = familyDebitService.getFamilyDebitAccount(id);
        System.out.println(familyDebitAccount.getObjectName());
    }
}
