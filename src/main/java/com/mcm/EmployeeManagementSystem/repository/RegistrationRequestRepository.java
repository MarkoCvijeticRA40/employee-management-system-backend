package com.mcm.EmployeeManagementSystem.repository;

import com.mcm.EmployeeManagementSystem.entity.RegistrationRequestEntity;
import com.mcm.EmployeeManagementSystem.entity.RegistrationRequestEntityStatus;

import java.util.List;

public interface RegistrationRequestRepository extends EntityRepository<RegistrationRequestEntity> {
    List<RegistrationRequestEntity> findByStatus(RegistrationRequestEntityStatus status);
}
