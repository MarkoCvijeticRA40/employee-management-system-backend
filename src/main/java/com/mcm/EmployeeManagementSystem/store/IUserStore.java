package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface IUserStore {

    public List<User> searchEngineers(String name, String surname, String email, LocalDateTime start, LocalDateTime end);
    public User save(User user);
    public List<User> findByRoleName(String roleName);
    public List<User> findByTitle(String title);
    public List<User> getAllEnabled();
    public User getById(Long id);
    List<User> getAllPotentialWorkers();
}
