package com.mcm.EmployeeManagementSystem.repository;

import com.mcm.EmployeeManagementSystem.entity.LogNotificationEntity;

import java.util.List;

public interface LogNotificationRepository extends EntityRepository<LogNotificationEntity> {
    List<LogNotificationEntity> findByRead(boolean read);
}
