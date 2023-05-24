package com.mcm.EmployeeManagementSystem.repository.acl;

import com.mcm.EmployeeManagementSystem.entity.acl.ACLClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ACLClassRepository extends JpaRepository<ACLClass, Long> {
}