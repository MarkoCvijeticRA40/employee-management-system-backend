package com.mcm.EmployeeManagementSystem.usecase.user;


import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
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

    public Response find(String email) {
        Response response;
        ValidationReport report = validator.validate(email);
        if (report.isValid()) {
            User user = store.find(email);
            response = new Response(report, user);
        } else {
            response = new Response(report, email);
        }

        return response;
    }
}
