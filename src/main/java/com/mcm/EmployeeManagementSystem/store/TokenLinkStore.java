package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.TokenLinkConverter;
import com.mcm.EmployeeManagementSystem.model.TokenLink;
import com.mcm.EmployeeManagementSystem.repository.TokenLinkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TokenLinkStore {
    private final TokenLinkRepository repository;
    private final TokenLinkConverter converter;

    public TokenLink find(String value) {
        return converter.toModel(repository.findByValue(value));
    }

    public TokenLink save(TokenLink tokenLink) {
        return converter.toModel(repository.save(converter.toEntity(tokenLink)));
    }
}
