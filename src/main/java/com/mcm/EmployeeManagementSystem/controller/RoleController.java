package com.mcm.EmployeeManagementSystem.controller;

import com.mcm.EmployeeManagementSystem.dto.AddPermissionToRoleRequest;
import com.mcm.EmployeeManagementSystem.dto.RemovePermissionFromRoleRequest;
import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.usecase.role.AddPermissionToRoleUseCase;
import com.mcm.EmployeeManagementSystem.usecase.role.RemovePermissionFromRoleUseCase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/roles")
public class RoleController {
    private final AddPermissionToRoleUseCase addPermissionToRoleUseCase;
    private final RemovePermissionFromRoleUseCase removePermissionFromRoleUseCase;

    @PutMapping("/add-permission")
    public Response add(@RequestBody AddPermissionToRoleRequest request) {
        return addPermissionToRoleUseCase.add(request);
    }

    @PutMapping("/remove-permission")
    public Response remove(@RequestBody RemovePermissionFromRoleRequest request) {
        return removePermissionFromRoleUseCase.remove(request);
    }
}
