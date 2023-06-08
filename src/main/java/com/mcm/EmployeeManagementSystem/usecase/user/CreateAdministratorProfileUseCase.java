package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.CreateAdministratorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAdministratorProfileUseCase {

    private final PasswordEncoder passwordEncoder;
    private final UserStore userStore;
    private final CreateAdministratorValidator validator;

    public Response register(User administrator) {

        ValidationReport report = validator.validate(administrator);

        if (report.isValid()) {
            if (userStore.exists(administrator.getEmail()) == false) {
                administrator.setStartOfWork(null);
                administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
                administrator.setAccountEnabled(true);
                administrator = userStore.save(administrator);
            }
        }
        return new Response(report, administrator);
    }
}
