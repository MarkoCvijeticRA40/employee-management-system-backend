package com.mcm.EmployeeManagementSystem.repository.acl;

import com.mcm.EmployeeManagementSystem.entity.acl.ACLEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ACLEntryRepository extends JpaRepository<ACLEntry, Long> {
}