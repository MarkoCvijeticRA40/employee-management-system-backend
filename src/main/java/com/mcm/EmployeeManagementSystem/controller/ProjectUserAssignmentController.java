package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.ProjectUserAssignment;
import com.mcm.EmployeeManagementSystem.model.Skill;
import com.mcm.EmployeeManagementSystem.usecase.projectuserassignment.EditProjectUserAssignmentUseCase;
import com.mcm.EmployeeManagementSystem.usecase.projectuserassignment.FindByUserUseCase;
import com.mcm.EmployeeManagementSystem.validator.projectuserassignment.EditProjectUserAssignmentValidator;
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

    @GetMapping("/user/{id}")
    public Response getAssignmentsByUserId(@PathVariable Long id) {
        return findByUserIdUseCase.findByUserId(id);
    }

    @PutMapping("/{id}")
    public Response update(@PathVariable Long id, @RequestBody ProjectUserAssignment projectUserAssignment) {
        return editProjectUserAssignmentUseCase.update(id, projectUserAssignment);
    }
}
