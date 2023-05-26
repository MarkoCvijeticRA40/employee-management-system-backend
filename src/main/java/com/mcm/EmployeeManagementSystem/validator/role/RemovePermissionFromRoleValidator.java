package com.mcm.EmployeeManagementSystem.validator.role;

import com.mcm.EmployeeManagementSystem.constant.Constant;
import com.mcm.EmployeeManagementSystem.constant.PermissionConstant;
import com.mcm.EmployeeManagementSystem.constant.RoleConstant;
import com.mcm.EmployeeManagementSystem.dto.RemovePermissionFromRoleRequest;
import com.mcm.EmployeeManagementSystem.model.Permission;
import com.mcm.EmployeeManagementSystem.model.Role;
import com.mcm.EmployeeManagementSystem.store.PermissionStore;
import com.mcm.EmployeeManagementSystem.store.RoleStore;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import com.mcm.EmployeeManagementSystem.validator.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class RemovePermissionFromRoleValidator implements Validator<RemovePermissionFromRoleRequest> {
    private final RoleStore roleStore;
    private final PermissionStore permissionStore;

    @Override
    public ValidationReport validate(RemovePermissionFromRoleRequest removePermissionFromRoleRequest) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (removePermissionFromRoleRequest == null) {
            report.setValid(false);
            report.addMessage(Constant.REQUEST_PARAMETER, "request parameter is null");
        } else {
            if (!roleStore.exists(removePermissionFromRoleRequest.getRoleName())) {
                report.setValid(false);
                report.addMessage(RoleConstant.ROLE, "role with passed name does not exist");
            }
            if (!permissionStore.exists(removePermissionFromRoleRequest.getPermissionName())) {
                report.setValid(false);
                report.addMessage(PermissionConstant.PERMISSION, "permission with passed name does not exist");
            }
            if (report.isValid()) {
                Role role = roleStore.find(removePermissionFromRoleRequest.getRoleName());
                if (role.getPermissions().stream()
                        .map(Permission::getName)
                        .noneMatch(name -> name.equals(removePermissionFromRoleRequest.getPermissionName()))) {
                    report.setValid(false);
                    report.addMessage(RoleConstant.ROLE, "role does not have this permission");
                }
            }
        }

        return report;

    }
}
