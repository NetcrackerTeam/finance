package com.netcracker.models;

import java.math.BigInteger;

public class User {
    private final BigInteger id;
    private final String name;
    private final String eMail;
    private String password;
    private PersonalDebitAccount personalDebitAccount;
    private FamilyDebitAccount familyDebitAccount;

    public static class Builder {
        private final BigInteger id;
        private final String name;
        private final String eMail;
        private String password;
        private PersonalDebitAccount personalDebitAccount;
        private FamilyDebitAccount familyDebitAccount;

        public Builder(BigInteger id, String name, String eMail, String password) {
            this.id = id;
            this.name = name;
            this.eMail = eMail;
            this.password = password;
        }

        public Builder personalDebit(PersonalDebitAccount val) {
            this.personalDebitAccount = val;
            return this;
        }

        public Builder familyDebit(FamilyDebitAccount val) {
            this.familyDebitAccount = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.eMail = builder.eMail;
        this.password = builder.password;
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPassword() {
        return password;
    }

    public PersonalDebitAccount getPersonalDebitAccount() {
        return personalDebitAccount;
    }

    public FamilyDebitAccount getFamilyDebitAccount() {
        return familyDebitAccount;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
