package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAdministratorProfileUseCase {

    private final PasswordEncoder passwordEncoder;

    private final UserStore userStore;

    public User register(User administrator) {
        administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
        administrator.setAccountEnabled(false);
        return userStore.save(administrator);
    }
}
