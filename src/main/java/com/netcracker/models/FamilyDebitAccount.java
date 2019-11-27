package com.netcracker.models;

import com.netcracker.models.enums.FamilyAccountStatusActive;

import java.util.List;

public class FamilyDebitAccount extends AbstractDebitAccount {
    private List<User> participants;
    private List<FamilyCreditAccount> familyCreditAccountList;
    private FamilyAccountStatusActive familyAccountStatus;

    public static class Builder extends BaseBuilder<FamilyDebitAccount, Builder> {

//        public Builder debitFamilyAccountList(List<User> listUser, List<FamilyCreditAccount> listFamilyDebitAccount) {
//            actualClass.setFamilyCreditAccountList(listFamilyDebitAccount);
//            return this;
//        }
//
//        public Builder debitFamilyUserList(List<User> listUser) {
//            actualClass.setParticipants(listUser);
//            return this;
//        }
        public Builder debitFamilyAccountStatus(FamilyAccountStatusActive familyAccountStatus) {
            actualClass.setStatus(familyAccountStatus);
            return this;
        }

        @Override
        protected FamilyDebitAccount getActual() {
            return new FamilyDebitAccount();
        }

        @Override
        protected Builder getActualBuilder() {
            return this;
        }
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<FamilyCreditAccount> getFamilyCreditAccountList() {
        return familyCreditAccountList;
    }

    public void setFamilyCreditAccountList(List<FamilyCreditAccount> familyCreditAccountList) {
        this.familyCreditAccountList = familyCreditAccountList;
    }

    public FamilyAccountStatusActive getStatus() { return familyAccountStatus; }

    public void setStatus(FamilyAccountStatusActive familyAccountStatus) { this.familyAccountStatus = familyAccountStatus; }
}
