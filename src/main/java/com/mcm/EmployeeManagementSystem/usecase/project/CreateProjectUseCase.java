package com.mcm.EmployeeManagementSystem.usecase.project;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.store.ProjectStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.project.CreateProjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectUseCase {

    private final CreateProjectValidator validator;
    private final ProjectStore projectStore;

    public Response create(Project project) {
        ValidationReport report = validator.validate(project);
        if (report.isValid()) {
            project = projectStore.save(project);
        }
        return new Response(report, project);
    }
}
