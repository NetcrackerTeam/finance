package com.netcracker.services;

import com.netcracker.models.PersonalCreditAccount;

public interface CreditService {

    public void createFamilyCredit();

    public void deleteFamilyCredit();

    public void createPersonalCredit();

    public void deletePersonalCredit();

    public void addFamilyCreditPayment();

    public void addPersonalCreditPayment();

    public void getPersonalCredits();

    public void getFamilyCredits();

    public void createDebt();

    public void changeDebt();

    public boolean isCommodityCredit();


}
