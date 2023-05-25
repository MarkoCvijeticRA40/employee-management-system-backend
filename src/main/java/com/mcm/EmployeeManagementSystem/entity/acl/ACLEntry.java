package com.mcm.EmployeeManagementSystem.entity.acl;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "acl_entry")
public class ACLEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long aclObjectIdentity;
    private int aceOrder;
    private Long sid;
    private int mask;
    private boolean granting;
    private boolean auditSuccess;
    private boolean auditFailure;
}