package com.netcracker.models;

import java.util.List;

public class FamilyDebitAccount extends AbstractDebitAccount {
    private List<User> participants;
    private List<FamilyCreditAccount> familyCreditAccountList;
    private static final int type_id = 13;


    public static class Builder extends BaseBuilder<FamilyDebitAccount, Builder> {

        public Builder DebitFamilyAccountList(List<User> listUser, List<FamilyCreditAccount> listFamilyDebitAccount) {
            actualClass.setFamilyCreditAccountList(listFamilyDebitAccount);
            return this;
        }

        public Builder DebitFamilyUserList(List<User> listUser) {
            actualClass.setParticipants(listUser);
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

    @Override
    public int getObjectTypeId() {
        return type_id;
    }

}
