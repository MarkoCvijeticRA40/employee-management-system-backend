package com.mcm.EmployeeManagementSystem.usecase.projectuserassignment;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import com.mcm.EmployeeManagementSystem.model.Skill;
import com.mcm.EmployeeManagementSystem.store.ProjectUserAssignmentStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.projectuserassignment.EditProjectUserAssignmentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditProjectUserAssignmentUseCase {
    private final ProjectUserAssignmentStore store;
    private final EditProjectUserAssignmentValidator validator;

    public Response update(Long id, ProjectUserAssignment projectUserAssignment) {
        ValidationReport report = validator.validate(projectUserAssignment);
        ProjectUserAssignment editedProjectUserAssignment = store.find(id);
        if (report.isValid()) {
            editedProjectUserAssignment = projectUserAssignment;
            editedProjectUserAssignment = store.save(editedProjectUserAssignment);
        }

        return new Response(report, editedProjectUserAssignment);
    }
}
