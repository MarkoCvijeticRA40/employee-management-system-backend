package com.mcm.EmployeeManagementSystem.validator.project;

import com.mcm.EmployeeManagementSystem.constant.ProjectConstant;
import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
@RequiredArgsConstructor
public class EditProjectValidator implements Validator<Project> {
    @Override
    public ValidationReport validate(Project project) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (project == null) {
            report.setValid(false);
            report.addMessage(ProjectConstant.Project, "project is null");
        } else {
            if (isBlank(project.getName())) {
                report.setValid(false);
                report.addMessage(ProjectConstant.Name, "name is blank");
            }
            if (isBlank(project.getDuration())) {
                report.setValid(false);
                report.addMessage(ProjectConstant.Duration, "duration is blank");
            }
            if (project.getUsers().isEmpty()) {
                report.setValid(false);
                report.addMessage(ProjectConstant.Users, "list of users is blank");
            }
        }
        return report;
    }
}
