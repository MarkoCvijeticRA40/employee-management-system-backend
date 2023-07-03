package com.mcm.EmployeeManagementSystem.repository;

import com.mcm.EmployeeManagementSystem.entity.SkillEntity;
import com.mcm.EmployeeManagementSystem.entity.UserEntity;

import java.util.List;

public interface SkillRepository extends EntityRepository<SkillEntity> {
    List<SkillEntity> findByUser(UserEntity userEntity);
}
