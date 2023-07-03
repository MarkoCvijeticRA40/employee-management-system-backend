package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.ActivationLinkConverter;
import com.mcm.EmployeeManagementSystem.model.ActivationLink;
import com.mcm.EmployeeManagementSystem.repository.ActivationLinkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ActivationLinkStore {
    private final ActivationLinkRepository repository;
    private final ActivationLinkConverter converter;

    public ActivationLink find(String value) {
        return converter.toModel(repository.findByValue(value));
    }

    public ActivationLink save(ActivationLink activationLink) {
        return converter.toModel(repository.save(converter.toEntity(activationLink)));
    }
}
