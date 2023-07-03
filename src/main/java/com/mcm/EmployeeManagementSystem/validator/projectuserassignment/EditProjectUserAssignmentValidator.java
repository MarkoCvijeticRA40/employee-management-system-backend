package com.mcm.EmployeeManagementSystem.validator.projectuserassignment;

import com.mcm.EmployeeManagementSystem.constant.ProjectUserAssignmentConstant;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Component
@RequiredArgsConstructor
public class EditProjectUserAssignmentValidator implements Validator<ProjectUserAssignment> {
    @Override
    public ValidationReport validate(ProjectUserAssignment projectUserAssignment) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (projectUserAssignment == null) {
            report.setValid(false);
            report.addMessage(ProjectUserAssignmentConstant.PROJECT_USER_ASSIGNMENT, "project user assignment is null");
        } else {
            if (projectUserAssignment.getUser() == null) {
                report.setValid(false);
                report.addMessage(ProjectUserAssignmentConstant.USER, "user is null");
            }
            if (projectUserAssignment.getProject() == null) {
                report.setValid(false);
                report.addMessage(ProjectUserAssignmentConstant.PROJECT, "project is null");
            }
            if (isBlank(projectUserAssignment.getWorkDescription())) {
                report.setValid(false);
                report.addMessage(ProjectUserAssignmentConstant.WORK_DESCRIPTION, "work description is blank");
            }
            if (projectUserAssignment.getStartOfWork() == null) {
                report.setValid(false);
                report.addMessage(ProjectUserAssignmentConstant.START_OF_WORK, "start of work is null");
            }
        }
        return report;
    }
}
