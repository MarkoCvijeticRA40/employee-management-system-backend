package com.mcm.EmployeeManagementSystem.converter;

import com.mcm.EmployeeManagementSystem.entity.ProjectUserAssignmentEntity;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ProjectUserAssignmentConverter implements GenericConverter<ProjectUserAssignment, ProjectUserAssignmentEntity> {

    private final ProjectConverter projectConverter;

    private final UserConverter userConverter;

    @Override
    public ProjectUserAssignment toModel(ProjectUserAssignmentEntity projectUserAssignmentEntity) {
        if (projectUserAssignmentEntity != null) {
            ProjectUserAssignment projectUserAssignment = new ProjectUserAssignment();
            projectUserAssignment.setId(projectUserAssignmentEntity.getId());
            projectUserAssignment.setProject(projectConverter.toModel(projectUserAssignmentEntity.getProject()));
            projectUserAssignment.setUser(userConverter.toModel(projectUserAssignmentEntity.getUser()));
            projectUserAssignment.setWorkDescription(projectUserAssignmentEntity.getWorkDescription());
            projectUserAssignment.setStartOfWork(projectUserAssignmentEntity.getStartOfWork());
            projectUserAssignment.setEndOfWork(projectUserAssignmentEntity.getEndOfWork());

            return projectUserAssignment;

        } else {
            return null;
        }
    }

    @Override
    public List<ProjectUserAssignment> toModel(List<ProjectUserAssignmentEntity> projectUserAssignmentEntities) {
        List<ProjectUserAssignment> projectUserAssignments = new ArrayList<>();
        for (ProjectUserAssignmentEntity projectUserAssignmentEntity : projectUserAssignmentEntities) {
            projectUserAssignments.add(this.toModel(projectUserAssignmentEntity));
        }

        return projectUserAssignments;
    }

    @Override
    public ProjectUserAssignmentEntity toEntity(ProjectUserAssignment projectUserAssignment) {
        ProjectUserAssignmentEntity projectUserAssignmentEntity = new ProjectUserAssignmentEntity();
        projectUserAssignmentEntity.setId(projectUserAssignment.getId());
        projectUserAssignmentEntity.setProject(projectConverter.toEntity(projectUserAssignment.getProject()));
        projectUserAssignmentEntity.setUser(userConverter.toEntity(projectUserAssignment.getUser()));
        projectUserAssignmentEntity.setWorkDescription(projectUserAssignment.getWorkDescription());
        projectUserAssignmentEntity.setStartOfWork(projectUserAssignment.getStartOfWork());
        projectUserAssignmentEntity.setEndOfWork(projectUserAssignment.getEndOfWork());

        return projectUserAssignmentEntity;
    }

    @Override
    public List<ProjectUserAssignmentEntity> toEntity(List<ProjectUserAssignment> projectUserAssignments) {
        List<ProjectUserAssignmentEntity> projectUserAssignmentEntities = new ArrayList<>();
        for (ProjectUserAssignment projectUserAssignment : projectUserAssignments) {
            projectUserAssignmentEntities.add(this.toEntity(projectUserAssignment));
        }

        return projectUserAssignmentEntities;
    }
}
