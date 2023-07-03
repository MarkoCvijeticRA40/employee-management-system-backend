package com.mcm.EmployeeManagementSystem.usecase.user;


import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.security.crypto.UserDecryptor;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.FindUserByEmailValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserByEmailUseCase {
    private final UserStore store;
    private final FindUserByEmailValidator validator;
    private final UserDecryptor userDecryptor;

    public Response find(String email) {
        Response response;
        ValidationReport report = validator.validate(email);
        if (report.isValid()) {
            User user = store.find(email);
            user = userDecryptor.decryptUser(user);
            response = new Response(report, user);
        } else {
            response = new Response(report, email);
        }

        return response;
    }
}
