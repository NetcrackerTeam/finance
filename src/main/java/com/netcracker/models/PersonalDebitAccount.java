package com.netcracker.models;

import java.util.List;

public class PersonalDebitAccount extends AbstractDebitAccount{
    private List<PersonalCreditAccount> personalCreditAccountList;

    public PersonalDebitAccount(List<PersonalCreditAccount> personalCreditAccountList) {
        this.personalCreditAccountList = personalCreditAccountList;
    }

    public List<PersonalCreditAccount> getPersonalCreditAccountList() {
        return personalCreditAccountList;
    }

    public void setPersonalCreditAccountList(List<PersonalCreditAccount> personalCreditAccountList) {
        this.personalCreditAccountList = personalCreditAccountList;
    }
}
