package com.netcracker.services;

import com.netcracker.models.FamilyDebitAccount;
import com.netcracker.models.PersonalDebitAccount;
import org.springframework.expression.Operation;

import java.util.List;

public interface OperationService {
    public void getAllFamilyOperations(List<FamilyDebitAccount> accounts);

    public void getAllPersonOperations(List<PersonalDebitAccount> accounts);

    public void getFamilyOperation();

    public void getPersonOperation();

    public void addFamilyOperation();

    public void addPersonOperation();

}
