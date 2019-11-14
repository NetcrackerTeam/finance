package com.netcracker.models;

import java.math.BigInteger;

public class User {
    private BigInteger id;
    private String name;
    private String eMail;
    private String password;
    private PersonalDebitAccount personalDebitAccount;
    private FamilyDebitAccount familyDebitAccount;
}
