package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.store.ProjectStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectStore store;

    public ProjectController(ProjectStore store) {
        this.store = store;
    }

    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        return store.save(project);
    }

    @GetMapping("")
    public List<Project> findAll() {
        return store.findAll();
    }

    @PutMapping("/update")
    public Project updateProject(@RequestBody Project project) {
        return store.save(project);
    }
}