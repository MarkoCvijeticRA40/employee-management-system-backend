package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.ProjectStore;
import com.mcm.EmployeeManagementSystem.store.ProjectUserAssignmentStore;
import com.mcm.EmployeeManagementSystem.usecase.project.AddEmployeeToProjectUseCase;
import com.mcm.EmployeeManagementSystem.usecase.project.CreateProjectUseCase;
import com.mcm.EmployeeManagementSystem.usecase.project.FindAllUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectStore store;
    private final ProjectUserAssignmentStore projectUserAssignmentStore;
    private final CreateProjectUseCase createProjectUseCase;
    private final AddEmployeeToProjectUseCase addEmployeeToProjectUseCase;
    private final FindAllUseCase findAllUseCase;

    @PostMapping("/create")
    public Response create(@RequestBody Project project) { return createProjectUseCase.create(project); }

    @GetMapping("")
    public List<Project> findAll() {
        return findAllUseCase.findAll();
    }

    @PutMapping("/update")
    public Response updateProject(@RequestBody Project project) {
        createProjectUserAssignment(project);
        return addEmployeeToProjectUseCase.update(project);
    }

    private Project getNewProject(Project project) {
        Project newProject = store.save(project);
        for (User user: project.getUsers()) {
            ProjectUserAssignment projectUserAssignment = new ProjectUserAssignment();
            projectUserAssignment.setProject(newProject);
            projectUserAssignment.setUser(user);
            projectUserAssignment.setStartOfWork(LocalDateTime.now());
            projectUserAssignmentStore.save(projectUserAssignment);
        }
        return newProject;
    }

    private void createProjectUserAssignment(Project project) {
        List<ProjectUserAssignment> projectUserAssignments = projectUserAssignmentStore.findByProject(project);
        for (User user: project.getUsers()) {
            boolean flag = true;
            for (ProjectUserAssignment projectUserAssignment: projectUserAssignments) {
                System.out.println(projectUserAssignment.getProject().getName());
                if (user.getId() == projectUserAssignment.getUser().getId()) {
                    flag = false;
                }
            }
            if (flag) {
                ProjectUserAssignment projectUserAssignment = new ProjectUserAssignment();
                projectUserAssignment.setProject(project);
                projectUserAssignment.setUser(user);
                projectUserAssignment.setStartOfWork(LocalDateTime.now());
                projectUserAssignmentStore.save(projectUserAssignment);
            }
        }
    }
}