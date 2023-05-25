package com.mcm.EmployeeManagementSystem.usecase.projectuserassignment;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.ProjectUserAssignmentStore;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.user.FindUserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindByUserUseCase {
    private final UserStore userStore;
    private final ProjectUserAssignmentStore projectUserAssignmentStore;
    private final FindUserValidator validator;

    public Response findByUserId(Long userId) {
        ValidationReport report = validator.validate(userId);
        User user = userStore.find(userId);
        List<ProjectUserAssignment> projectUserAssignments = new ArrayList<ProjectUserAssignment>();
        if (report.isValid()) {
            projectUserAssignments = projectUserAssignmentStore.findByUser(user);
        }

        return new Response(report, projectUserAssignments);
    }
}
