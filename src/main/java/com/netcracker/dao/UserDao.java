package com.netcracker.dao;

import com.netcracker.models.User;

public interface UserDao {
    public void createUser();
    public User getUserById();
    public User getUserByLogin();
    public void deleteUser();
    public void  updateUserPasswordById();
    public User getUsersByFamilyAccountId();
    public User getUserByFamilyAccountId();
}
