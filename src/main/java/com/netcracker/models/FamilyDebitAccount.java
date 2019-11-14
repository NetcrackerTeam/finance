package com.netcracker.models;

import java.util.List;

public class FamilyDebitAccount extends AbstractDebitAccount {
    private List<User> participants;
    private List<FamilyCreditAccount> familyCreditAccountList;
}
