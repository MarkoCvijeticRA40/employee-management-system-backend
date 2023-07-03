package com.mcm.EmployeeManagementSystem.usecase.user;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.FindUsersByRoleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FindByRoleNameUseCase {

    private final UserStore store;
    private final FindUsersByRoleValidator validator;

    public Response findByRoleName(String roleName) {
        ValidationReport report = validator.validate(roleName);

        if (!report.isValid()) {
            return new Response(report, new ArrayList<>());
        }

        List<User> users = store.getAllEnabled();

        List<User> foundedUsers = users.stream()
                .filter(user -> user.getRoleNames().contains(roleName))
                .collect(Collectors.toList());

        return new Response(report, foundedUsers);
    }
}
