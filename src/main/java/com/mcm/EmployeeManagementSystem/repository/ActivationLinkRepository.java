package com.mcm.EmployeeManagementSystem.repository;

import com.mcm.EmployeeManagementSystem.entity.ActivationLinkEntity;

public interface ActivationLinkRepository extends EntityRepository<ActivationLinkEntity> {
    boolean existsByValue(String value);

    ActivationLinkEntity findByValue(String value);
}
