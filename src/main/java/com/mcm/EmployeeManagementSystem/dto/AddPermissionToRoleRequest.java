package com.mcm.EmployeeManagementSystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddPermissionToRoleRequest {
    private String roleName;
    private String permissionName;
}
