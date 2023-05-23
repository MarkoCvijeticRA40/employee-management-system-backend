package com.mcm.EmployeeManagementSystem.converter;

import com.mcm.EmployeeManagementSystem.entity.SkillEntity;
import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.entity.ProjectEntity;
import com.mcm.EmployeeManagementSystem.model.Skill;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ProjectConverter implements GenericConverter<Project, ProjectEntity>{

    private final UserConverter userConverter;

    @Override
    public Project toModel(ProjectEntity projectEntity) {
        if (projectEntity != null) {
            Project project = new Project();
            project.setId(projectEntity.getId());
            project.setName(projectEntity.getName());
            project.setDuration(projectEntity.getDuration());
            project.setUsers(userConverter.toModel(projectEntity.getUsers()));

            return project;
        } else {
            return null;
        }
    }

    @Override
    public List<Project> toModel(List<ProjectEntity> projectEntities) {
        List<Project> projects = new ArrayList<>();
        for (ProjectEntity projectEntity : projectEntities) {
            projects.add(this.toModel(projectEntity));
        }

        return projects;
    }

    @Override
    public ProjectEntity toEntity(Project project) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(project.getId());
        projectEntity.setName(project.getName());
        projectEntity.setDuration(project.getDuration());
        projectEntity.setUsers(userConverter.toEntity(project.getUsers()));

        return projectEntity;
    }

    @Override
    public List<ProjectEntity> toEntity(List<Project> projects) {
        List<ProjectEntity> projectEntities = new ArrayList<>();
        for (Project project : projects) {
            projectEntities.add(this.toEntity(project));
        }

        return projectEntities;
    }
}
