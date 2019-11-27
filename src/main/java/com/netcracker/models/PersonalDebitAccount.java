package com.netcracker.models;

import com.netcracker.models.enums.PersonalAccountStatusActive;

import java.util.List;

public class PersonalDebitAccount extends AbstractDebitAccount {
    private List<PersonalCreditAccount> personalCreditAccountList;
    private PersonalAccountStatusActive personalAccountStatusActive;

    public static class Builder extends BaseBuilder<PersonalDebitAccount, Builder> {

        public Builder DebitPersonalAccountList(List<PersonalCreditAccount> list) {
            actualClass.setPersonalCreditAccountList(list);
            return this;
        }
        public Builder debitPersonalAccountStatus(PersonalAccountStatusActive personalAccountStatus) {
            actualClass.setStatus(personalAccountStatus);
            return this;
        }

        @Override
        protected PersonalDebitAccount getActual() {
            return new PersonalDebitAccount();
        }

        @Override
        protected Builder getActualBuilder() {
            return this;
        }
    }


    public List<PersonalCreditAccount> getPersonalCreditAccountList() {
        return personalCreditAccountList;
    }

    public void setPersonalCreditAccountList(List<PersonalCreditAccount> personalCreditAccountList) {
        this.personalCreditAccountList = personalCreditAccountList;
    }
    public PersonalAccountStatusActive getStatus() { return personalAccountStatusActive; }

    public void setStatus(PersonalAccountStatusActive personalAccountStatus) { this.personalAccountStatusActive = personalAccountStatus; }
}