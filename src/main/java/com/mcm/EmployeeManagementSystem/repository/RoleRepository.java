package com.mcm.EmployeeManagementSystem.repository;

import com.mcm.EmployeeManagementSystem.entity.RoleEntity;

public interface RoleRepository extends EntityRepository<RoleEntity> {
    RoleEntity findByName(String name);

    boolean existsByName(String name);
}
