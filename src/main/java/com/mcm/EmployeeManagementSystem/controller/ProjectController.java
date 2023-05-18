package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.model.Project;
import com.mcm.EmployeeManagementSystem.store.ProjectStore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectStore store;

    @PostMapping("/create")
    public Project createProject(@RequestBody Project project) {
        return store.save(project);
    }

}
