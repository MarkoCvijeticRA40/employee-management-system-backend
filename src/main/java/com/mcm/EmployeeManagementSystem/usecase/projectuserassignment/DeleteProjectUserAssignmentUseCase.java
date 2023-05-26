package com.mcm.EmployeeManagementSystem.usecase.projectuserassignment;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import com.mcm.EmployeeManagementSystem.store.ProjectUserAssignmentStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.projectuserassignment.FindProjectUserAssignmentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProjectUserAssignmentUseCase {
    private final ProjectUserAssignmentStore store;
    private final FindProjectUserAssignmentValidator validator;

    public Response delete(Long id) {
        ValidationReport report = validator.validate(id);
        ProjectUserAssignment projectUserAssignment = new ProjectUserAssignment();
        if (report.isValid()) {
            projectUserAssignment = store.find(id);
            store.delete(projectUserAssignment);
        }

        return new Response(report, projectUserAssignment.getId());
    }
}
