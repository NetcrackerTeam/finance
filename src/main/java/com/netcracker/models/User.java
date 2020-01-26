package com.netcracker.models;

import com.netcracker.models.enums.UserRole;
import com.netcracker.models.enums.UserStatusActive;

import java.math.BigInteger;

public class User {
    private  BigInteger id;
    private  String name;
    private  String eMail;
    private String password;
    private BigInteger personal_id;
    private BigInteger family_id;
    private UserStatusActive  userStatusActive;
    private UserRole userRole;
    private double totalIncome;
    private double totalExpense;
    private String confirmPassword;
    private String oldPassword;

    public User() {
    }

    public static class Builder {
        private  BigInteger id;
        private  String name;
        private  String eMail;
        private String password;
        private BigInteger personal_id;
        private BigInteger family_id;
        private UserStatusActive  userStatusActive;
        private UserRole userRole;
        private double totalIncome;
        private double totalExpense;

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


        public Builder personalDebit(BigInteger val) {
            this.personal_id = val;
            return this;
        }

        public Builder familyDebit(BigInteger val) {
            this.family_id = val;
            return this;
        }

        public Builder userActive(UserStatusActive val){
            this.userStatusActive = val;
            return this;
        }
        public Builder userRole(UserRole val){
            this.userRole = val;
            return this;
        }
        public Builder userIncome(double income) {
            this.totalIncome = income;
            return this;
        }
        public Builder userExpense(double expense) {
            this.totalExpense = expense;
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
        this.userStatusActive = builder.userStatusActive;
        this.personal_id = builder.personal_id;
        this.family_id = builder.family_id;
        this.userRole = builder.userRole;
        this.totalIncome = builder.totalIncome;
        this.totalExpense = builder.totalExpense;
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

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserStatusActive(UserStatusActive userStatusActive) {
        this.userStatusActive = userStatusActive;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public BigInteger getPersonalDebitAccount() {
        return personal_id;
    }

    public BigInteger getFamilyDebitAccount() {
        return family_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public UserStatusActive getUserStatusActive() {
        return userStatusActive;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public double getIncome() {
        return  totalIncome;
    }

    public double getExpense() {
        return  totalExpense;
    }

    public void setIncome(double income) {
        this.totalIncome = income;
    }
    public void setExpense(double expense) {
        this.totalExpense = expense;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }
}
