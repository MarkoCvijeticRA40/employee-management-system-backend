package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import com.mcm.EmployeeManagementSystem.model.User;
import com.mcm.EmployeeManagementSystem.store.ProjectStore;
import com.mcm.EmployeeManagementSystem.store.ProjectUserAssignmentStore;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectStore store;
    private final ProjectUserAssignmentStore projectUserAssignmentStore;

    public ProjectController(ProjectStore store, ProjectUserAssignmentStore projectUserAssignmentStore) {
        this.store = store;
        this.projectUserAssignmentStore = projectUserAssignmentStore;
    }

    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        return getNewProject(project);
    }

    @GetMapping("")
    public List<Project> findAll() {
        return store.findAll();
    }

    @PutMapping("/update")
    public Project updateProject(@RequestBody Project project) {
        createProjectUserAssignment(project);
        return store.save(project);
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