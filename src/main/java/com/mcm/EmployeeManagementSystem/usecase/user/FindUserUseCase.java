package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.FindUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FindUserUseCase {
    private final UserStore store;
    private final FindUserValidator validator;

    public Response find(Long id) {
        ValidationReport report = validator.validate(id);
        User user = new User();
        if (report.isValid()) {
            user = store.find(id);
        }

        return new Response(report, user);
    }
}
