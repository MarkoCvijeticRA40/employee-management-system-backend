package com.mcm.EmployeeManagementSystem.repository.acl;

import com.mcm.EmployeeManagementSystem.entity.acl.ACLSID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ACLSIDRepository extends JpaRepository<ACLSID, Long> {
}