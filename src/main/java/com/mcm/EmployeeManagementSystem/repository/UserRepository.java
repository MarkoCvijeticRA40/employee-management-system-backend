package com.mcm.EmployeeManagementSystem.repository;

import com.mcm.EmployeeManagementSystem.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends EntityRepository<UserEntity> {
    default UserEntity findUserByEmail(String email) {
        return findByEmail(email).orElse(null);
    }

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UserEntity> findByRoles_Name(String roleName);
}
