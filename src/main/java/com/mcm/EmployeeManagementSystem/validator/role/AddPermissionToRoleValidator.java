package com.mcm.EmployeeManagementSystem.validator.role;

import com.mcm.EmployeeManagementSystem.constant.Constant;
import com.mcm.EmployeeManagementSystem.constant.PermissionConstant;
import com.mcm.EmployeeManagementSystem.constant.RoleConstant;
import com.mcm.EmployeeManagementSystem.dto.AddPermissionToRoleRequest;
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
public class AddPermissionToRoleValidator implements Validator<AddPermissionToRoleRequest> {
    private final RoleStore roleStore;
    private final PermissionStore permissionStore;

    @Override
    public ValidationReport validate(AddPermissionToRoleRequest addPermissionToRoleRequest) {
        ValidationReport report = new ValidationReport(true, new HashMap<>());
        if (addPermissionToRoleRequest == null) {
            report.setValid(false);
            report.addMessage(Constant.REQUEST_PARAMETER, "request parameter is null");
        } else {
            if (!roleStore.exists(addPermissionToRoleRequest.getRoleName())) {
                report.setValid(false);
                report.addMessage(RoleConstant.ROLE, "role with passed name does not exist");
            }
            if (!permissionStore.exists(addPermissionToRoleRequest.getPermissionName())) {
                report.setValid(false);
                report.addMessage(PermissionConstant.PERMISSION, "permission with passed name does not exist");
            }
            if (report.isValid()) {
                Role role = roleStore.find(addPermissionToRoleRequest.getRoleName());
                if (role.getPermissions().stream()
                        .map(Permission::getName)
                        .anyMatch(name -> name.equals(addPermissionToRoleRequest.getPermissionName()))) {
                    report.setValid(false);
                    report.addMessage(RoleConstant.ROLE, "role already has this permission");
                }
            }
        }

        return report;

    }
}
