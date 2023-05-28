package com.mcm.EmployeeManagementSystem.usecase.projectuserassignment;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.ProjectStore;
import com.mcm.EmployeeManagementSystem.store.ProjectUserAssignmentStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.project.FindProjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindByProjectUseCase {
    private final ProjectStore projectStore;
    private final ProjectUserAssignmentStore projectUserAssignmentStore;
    private final FindProjectValidator validator;

    public Response findByProject(Long projectId) {
        ValidationReport report = validator.validate(projectId);
        Project project = projectStore.find(projectId);
        List<ProjectUserAssignment> projectUserAssignments = new ArrayList<ProjectUserAssignment>();
        if (report.isValid()) {
            projectUserAssignments = projectUserAssignmentStore.findByProject(project);
        }

        return new Response(report, projectUserAssignments);
    }
}
