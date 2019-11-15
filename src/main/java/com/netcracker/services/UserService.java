package com.netcracker.services;

import com.netcracker.models.User;

public interface UserService {
    public void changePassword(String newPassword);
    public void createUser();
    public void createPersonalDebitAcc();
    public boolean isUserActive(User user);
    public boolean isUserHasFamilyAccount(User user);
}
