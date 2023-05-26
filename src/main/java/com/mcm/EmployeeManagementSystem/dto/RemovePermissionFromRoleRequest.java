package com.mcm.EmployeeManagementSystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RemovePermissionFromRoleRequest {
    private String roleName;
    private String permissionName;
}
