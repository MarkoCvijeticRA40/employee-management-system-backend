package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.model.User;

import java.util.Collection;
import java.util.List;

public interface IUserStore {

    public List<User> findUsers(String name, String surname, String email);
    User save(User user);
    public List<User> findAll();


}
