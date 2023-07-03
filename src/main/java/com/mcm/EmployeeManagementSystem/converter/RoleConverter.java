package com.mcm.EmployeeManagementSystem.converter;

import com.mcm.EmployeeManagementSystem.entity.RoleEntity;
import com.mcm.EmployeeManagementSystem.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class RoleConverter implements GenericConverter<Role, RoleEntity> {
    private final PermissionConverter permissionConverter;

    @Override
    public Role toModel(RoleEntity roleEntity) {
        if (roleEntity != null) {
            Role role = new Role();
            role.setId(roleEntity.getId());
            role.setName(roleEntity.getName());
            role.setPermissions(permissionConverter.toModel(roleEntity.getPermissions()));

            return role;
        } else {
            return null;
        }
    }

    @Override
    public List<Role> toModel(List<RoleEntity> roleEntities) {
        List<Role> roles = new ArrayList<>();
        for (RoleEntity roleEntity : roleEntities) {
            roles.add(this.toModel(roleEntity));
        }

        return roles;
    }

    @Override
    public RoleEntity toEntity(Role role) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(role.getId());
        roleEntity.setName(role.getName());
        roleEntity.setPermissions(permissionConverter.toEntity(role.getPermissions()));

        return roleEntity;
    }

    @Override
    public List<RoleEntity> toEntity(List<Role> roles) {
        List<RoleEntity> roleEntities = new ArrayList<>();
        for (Role role : roles) {
            roleEntities.add(this.toEntity(role));
        }

        return roleEntities;
    }
}
