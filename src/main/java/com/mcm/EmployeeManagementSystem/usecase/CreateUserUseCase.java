package com.mcm.EmployeeManagementSystem.usecase;

import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateUserUseCase {

    private final UserStore userStore;

    public User save(User user) {
        return userStore.save(user);
    }

}
