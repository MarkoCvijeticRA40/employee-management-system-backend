package com.mcm.EmployeeManagementSystem.usecase;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class UserSearchUseCase {

    private final UserStore userStore;

    public Response findUsers(String email, String name, String lastname) {
        Response response;
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        Collection<User> users = userStore.findUsers(email, name, lastname);
        response = new Response(report, users);
        return response;
    }
}
