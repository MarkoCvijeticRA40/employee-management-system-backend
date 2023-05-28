package com.mcm.EmployeeManagementSystem.repository;

import com.mcm.EmployeeManagementSystem.entity.ProjectEntity;
import com.mcm.EmployeeManagementSystem.entity.ProjectUserAssignmentEntity;
import com.mcm.EmployeeManagementSystem.entity.UserEntity;

import java.util.List;

public interface ProjectUserAssignmentRepository extends EntityRepository<ProjectUserAssignmentEntity> {
    List<ProjectUserAssignmentEntity> findByUser(UserEntity userEntity);
    List<ProjectUserAssignmentEntity> findByProject(ProjectEntity projectEntity);
}
