package com.netcracker.services;

public interface FamilyDebitService {
    public void createFamilyDebitAccount();

    public void deleteFamilyDebitAccount();

    public void addCreditAccount();

    public void addUserToAccount();

    public void deleteUserFromAccount();

    // не знаю что вернуть
    public void getHistory();
}
