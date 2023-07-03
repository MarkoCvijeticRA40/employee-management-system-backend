package com.mcm.EmployeeManagementSystem.converter;

import com.mcm.EmployeeManagementSystem.entity.PermissionEntity;
import com.mcm.EmployeeManagementSystem.model.Permission;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionConverter implements GenericConverter<Permission, PermissionEntity> {
    @Override
    public Permission toModel(PermissionEntity permissionEntity) {
        if (permissionEntity != null) {
            Permission permission = new Permission();
            permission.setId(permissionEntity.getId());
            permission.setName(permissionEntity.getName());

            return permission;
        } else {
            return null;
        }
    }

    @Override
    public List<Permission> toModel(List<PermissionEntity> permissionEntities) {
        List<Permission> permissions = new ArrayList<>();
        for (PermissionEntity permissionEntity : permissionEntities) {
            permissions.add(this.toModel(permissionEntity));
        }

        return permissions;
    }

    @Override
    public PermissionEntity toEntity(Permission permission) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setId(permission.getId());
        permissionEntity.setName(permission.getName());

        return permissionEntity;
    }

    @Override
    public List<PermissionEntity> toEntity(List<Permission> permissions) {
        List<PermissionEntity> permissionEntities = new ArrayList<>();
        for (Permission permission : permissions) {
            permissionEntities.add(this.toEntity(permission));
        }

        return permissionEntities;
    }
}
