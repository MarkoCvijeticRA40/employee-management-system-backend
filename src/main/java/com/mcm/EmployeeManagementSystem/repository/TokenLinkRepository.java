package com.mcm.EmployeeManagementSystem.repository;

import com.mcm.EmployeeManagementSystem.entity.TokenLinkEntity;

public interface TokenLinkRepository extends EntityRepository<TokenLinkEntity> {
    boolean existsByValue(String value);

    TokenLinkEntity findByValue(String value);
}
