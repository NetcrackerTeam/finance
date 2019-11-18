package com.netcracker.models;

import java.util.List;

public class FamilyDebitAccount extends AbstractDebitAccount {
    private List<User> participants;
    private List<FamilyCreditAccount> familyCreditAccountList;

    public FamilyDebitAccount(List<User> participants) {
        this.participants = participants;
    }

    public FamilyDebitAccount(List<User> participants, List<FamilyCreditAccount> familyCreditAccountList) {
        this.participants = participants;
        this.familyCreditAccountList = familyCreditAccountList;
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
}
