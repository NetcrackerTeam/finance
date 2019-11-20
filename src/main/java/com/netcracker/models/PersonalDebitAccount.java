package com.netcracker.models;

import java.util.List;

public class PersonalDebitAccount extends AbstractDebitAccount {
    private List<PersonalCreditAccount> personalCreditAccountList;

    @Override
    public int getObjectTypeId() {
        return 0;
    }

    public static class Builder extends BaseBuilder<PersonalDebitAccount, Builder> {

        public Builder DebitPersonalAccountList(List<PersonalCreditAccount> list) {
            actualClass.setPersonalCreditAccountList(list);
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

}
