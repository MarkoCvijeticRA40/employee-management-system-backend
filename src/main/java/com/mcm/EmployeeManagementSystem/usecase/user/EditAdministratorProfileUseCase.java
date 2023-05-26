package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditAdministratorProfileUseCase {

    private final PasswordEncoder passwordEncoder;

    private final UserStore userStore;

    public User updateAdministrator(User administrator) {
        if (IsAdministratorPasswordChanged(administrator.getPassword(), administrator.getId()) == true) {
            administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
            return userStore.save(administrator);
        }
        return userStore.save(administrator);
    }

    public boolean IsAdministratorPasswordChanged(String password, Long id) {
        User administrator = userStore.getById(id);
        if (administrator.getPassword().equals(password)) {
            return false;
        }
        return true;
    }
}
