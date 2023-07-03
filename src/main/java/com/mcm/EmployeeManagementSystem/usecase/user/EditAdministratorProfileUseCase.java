package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.crypto.UserEncryptor;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.EditUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditAdministratorProfileUseCase {

    private final PasswordEncoder passwordEncoder;
    private final UserStore userStore;
    private final EditUserValidator validator;
    private final UserEncryptor userEncryptor;

    public Response updateAdministrator(User administrator) {
        administrator = userEncryptor.encryptUser(administrator);
        ValidationReport report = validator.validate(administrator);
        if (report.isValid()) {
            if (IsAdministratorPasswordChanged(administrator.getPassword(), administrator.getId()) == true) {
                administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
            }
            administrator = userStore.save(administrator);
        }
        return new Response(report, administrator);
    }

    public boolean IsAdministratorPasswordChanged(String password, Long id) {
        User administrator = userStore.getById(id);
        if (administrator.getPassword().equals(password)) {
            return false;
        }
        return true;
    }
}
