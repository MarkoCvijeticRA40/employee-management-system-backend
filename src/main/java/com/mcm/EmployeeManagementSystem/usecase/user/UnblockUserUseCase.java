package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.crypto.UserEncryptor;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.UnblockUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnblockUserUseCase {

    private final UserStore userStore;
    private final UnblockUserValidator validator;
    private final UserEncryptor userEncryptor;

    public Response unblock(User user) {
        user.setAccountEnabled(true);
        user = userEncryptor.encryptUser(user);
        ValidationReport report = validator.validate(user);
        if (report.isValid()) {
            user = userStore.save(user);
        }
        return new Response(report, user);
    }
}
