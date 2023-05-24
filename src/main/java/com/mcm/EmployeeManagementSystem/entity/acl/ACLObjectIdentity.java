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
@Entity(name = "acl_object_identity")
public class ACLObjectIdentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String objectClass;
    private String objectIdIdentity;
    private Long parentObject;
    private Long ownerSid;
    private boolean entriesInheriting;
}