package com.mcm.EmployeeManagementSystem.usecase.role;

import com.mcm.EmployeeManagementSystem.dto.RemovePermissionFromRoleRequest;
import com.mcm.EmployeeManagementSystem.dto.Response;
import com.mcm.EmployeeManagementSystem.model.Permission;
import com.mcm.EmployeeManagementSystem.model.Role;
import com.mcm.EmployeeManagementSystem.store.PermissionStore;
import com.mcm.EmployeeManagementSystem.store.RoleStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.role.RemovePermissionFromRoleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RemovePermissionFromRoleUseCase {
    private final RemovePermissionFromRoleValidator validator;
    private final RoleStore roleStore;
    private final PermissionStore permissionStore;

    public Response remove(RemovePermissionFromRoleRequest request) {
        ValidationReport report = validator.validate(request);
        if (report.isValid()) {
            Role role = roleStore.find(request.getRoleName());
            Permission permission = permissionStore.find(request.getPermissionName());
            List<Permission> permissions = role.getPermissions();
            for (Permission p : permissions) {
                if (p.getName().equals(permission.getName())) {
                    permissions.remove(p);
                    break;
                }
            }
            role.setPermissions(permissions);
            roleStore.save(role);
        }

        return new Response(report, request.getPermissionName());
    }
}
