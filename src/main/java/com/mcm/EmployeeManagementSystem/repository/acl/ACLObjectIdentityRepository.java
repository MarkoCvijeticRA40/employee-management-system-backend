package com.mcm.EmployeeManagementSystem.repository.acl;

import com.mcm.EmployeeManagementSystem.entity.acl.ACLObjectIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ACLObjectIdentityRepository extends JpaRepository<ACLObjectIdentity, Long> {
}