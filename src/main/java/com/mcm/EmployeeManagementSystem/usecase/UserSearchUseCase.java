package com.mcm.EmployeeManagementSystem.usecase;

import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserSearchUseCase {

    private final UserStore userStore;

    public List<User> searchEngineers(String email, String name, String lastname, LocalDateTime startDate, LocalDateTime endDate) {
        List<User> users = userStore.searchEngineers(email, name, lastname, startDate, endDate);
        return users;
    }

    public User save(User appUser) {
        return userStore.save(appUser);
    }

    public List<User> findByRoleName(String roleName) {
        return userStore.findByRoleName(roleName);
    }

    public List<User> findByTitle(String title) {
        return userStore.findByTitle(title);
    }
}
