package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.crypto.UserEncryptor;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.BlockUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockUserUseCase {

    private final PasswordEncoder passwordEncoder;
    private final UserStore userStore;
    private final BlockUserValidator validator;
    private final UserEncryptor userEncryptor;

    public Response block(User user) {
        user = userEncryptor.encryptUser(user);
        ValidationReport report = validator.validate(user);
        if (report.isValid()) {
            user = userStore.save(user);
        }
        return new Response(report, user);
    }
}
