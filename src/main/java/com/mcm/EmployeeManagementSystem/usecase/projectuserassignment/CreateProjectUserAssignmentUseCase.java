package com.mcm.EmployeeManagementSystem.usecase.projectuserassignment;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.ProjectStore;
import com.mcm.EmployeeManagementSystem.store.ProjectUserAssignmentStore;
import com.mcm.EmployeeManagementSystem.store.UserStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.projectuserassignment.CreateProjectUserAssignmentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectUserAssignmentUseCase {
    private final UserStore userStore;
    private final ProjectStore projectStore;
    private final ProjectUserAssignmentStore projectUserAssignmentStore;
    private final CreateProjectUserAssignmentValidator validator;

    public Response create(com.mcm.EmployeeManagementSystem.dto.ProjectUserAssignment projectUserAssignment) {
        ProjectUserAssignment createdProjectUserAssignment = getProjectUserAssignment(projectUserAssignment);
        ValidationReport report = validator.validate(createdProjectUserAssignment);
        if (report.isValid()) {
            createdProjectUserAssignment = projectUserAssignmentStore.save(createdProjectUserAssignment);
        }

        return new Response(report, createdProjectUserAssignment);
    }

    private ProjectUserAssignment getProjectUserAssignment(com.mcm.EmployeeManagementSystem.dto.ProjectUserAssignment projectUserAssignment) {
        User user = userStore.find(projectUserAssignment.getUserId());
        Project project = projectStore.find(projectUserAssignment.getProjectId());
        ProjectUserAssignment createdProjectUserAssignment = new ProjectUserAssignment();
        createdProjectUserAssignment.setUser(user);
        createdProjectUserAssignment.setProject(project);
        createdProjectUserAssignment.setWorkDescription(projectUserAssignment.getWorkDescription());
        createdProjectUserAssignment.setStartOfWork(projectUserAssignment.getStartOfWork());
        createdProjectUserAssignment.setEndOfWork(projectUserAssignment.getEndOfWork());
        return createdProjectUserAssignment;
    }
}
