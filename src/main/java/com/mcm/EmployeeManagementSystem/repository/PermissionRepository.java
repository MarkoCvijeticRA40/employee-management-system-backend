package com.mcm.EmployeeManagementSystem.repository;

import com.mcm.EmployeeManagementSystem.entity.PermissionEntity;

public interface PermissionRepository extends EntityRepository<PermissionEntity> {
    boolean existsByName(String name);

    PermissionEntity findByName(String name);
}
