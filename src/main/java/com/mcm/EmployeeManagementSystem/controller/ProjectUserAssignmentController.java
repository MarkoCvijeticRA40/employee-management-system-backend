package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import com.mcm.EmployeeManagementSystem.usecase.projectuserassignment.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/assignments")
public class ProjectUserAssignmentController {
    private final FindByUserUseCase findByUserIdUseCase;
    private final EditProjectUserAssignmentUseCase editProjectUserAssignmentUseCase;
    private final FindByProjectUseCase findByProjectUseCase;
    private final DeleteProjectUserAssignmentUseCase deleteProjectUserAssignmentUseCase;
    private final CreateProjectUserAssignmentUseCase createProjectUserAssignmentUseCase;

    @PostMapping
    public Response create(@RequestBody com.mcm.EmployeeManagementSystem.dto.ProjectUserAssignment projectUserAssignment) {
        return createProjectUserAssignmentUseCase.create(projectUserAssignment);
    }
    @GetMapping("/user/{id}")
    public Response getAssignmentsByUserId(@PathVariable Long id) {
        return findByUserIdUseCase.findByUserId(id);
    }

    @GetMapping("/project/{id}")
    public Response getAssignmentsByProjectId(@PathVariable Long id) {
        return findByProjectUseCase.findByProject(id);
    }

    @PutMapping("/{id}")
    public Response update(@PathVariable Long id, @RequestBody ProjectUserAssignment projectUserAssignment) {
        return editProjectUserAssignmentUseCase.update(id, projectUserAssignment);
    }
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        return deleteProjectUserAssignmentUseCase.delete(id);
    }
}
