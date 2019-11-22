package com.netcracker.models;

import java.math.BigInteger;

public class User {
    private  BigInteger id;
    private  String name;
    private  String eMail;
    private String password;
    private PersonalDebitAccount personalDebitAccount;
    private FamilyDebitAccount familyDebitAccount;

    public static class Builder {
        private  BigInteger id;
        private  String name;
        private  String eMail;
        private String password;
        private PersonalDebitAccount personalDebitAccount;
        private FamilyDebitAccount familyDebitAccount;

        public Builder() {
        }

        public Builder user_id(BigInteger id){
            this.id = id;
            return this;
        }
        public Builder user_name(String name ){
            this.name = name;
            return this;
        }
        public Builder user_eMail(String eMail ){
            this.eMail = eMail;
            return this;
        }

        public Builder user_password(String password ){
            this.password= password;
            return this;
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
