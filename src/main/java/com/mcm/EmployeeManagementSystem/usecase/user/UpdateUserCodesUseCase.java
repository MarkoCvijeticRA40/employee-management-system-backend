package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.crypto.UserEncryptor;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.UpdateUserCodesValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserCodesUseCase {

    private final UserStore store;
    private final UpdateUserCodesValidator validator;
    private final UserEncryptor userEncryptor;

    public Response update(User user) {
        user = userEncryptor.encryptUser(user);
        ValidationReport report = validator.validate(user);
        if (report.isValid()) {
            user = store.save(user);
        }
        return new Response(report, user);
    }
}
