package com.mcm.EmployeeManagementSystem.usecase.role;

import com.mcm.EmployeeManagementSystem.dto.AddPermissionToRoleRequest;
import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Permission;
import com.mcm.EmployeeManagementSystem.model.Role;
import com.mcm.EmployeeManagementSystem.store.PermissionStore;
import com.mcm.EmployeeManagementSystem.store.RoleStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.role.AddPermissionToRoleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddPermissionToRoleUseCase {
    private final AddPermissionToRoleValidator validator;
    private final RoleStore roleStore;
    private final PermissionStore permissionStore;

    public Response add(AddPermissionToRoleRequest request) {
        ValidationReport report = validator.validate(request);
        if (report.isValid()) {
            Role role = roleStore.find(request.getRoleName());
            Permission permission = permissionStore.find(request.getPermissionName());
            List<Permission> permissions = role.getPermissions();
            permissions.add(permission);
            role.setPermissions(permissions);
            roleStore.save(role);
        }

        return new Response(report, request.getPermissionName());
    }
}
