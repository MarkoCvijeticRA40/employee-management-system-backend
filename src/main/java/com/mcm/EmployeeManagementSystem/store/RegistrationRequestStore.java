package com.mcm.EmployeeManagementSystem.store;

import com.mcm.EmployeeManagementSystem.converter.RegistrationRequestConverter;
import com.mcm.EmployeeManagementSystem.model.RegistrationRequest;
import com.mcm.EmployeeManagementSystem.repository.RegistrationRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RegistrationRequestStore {
    private final RegistrationRequestRepository repository;
    private final RegistrationRequestConverter converter;

    public RegistrationRequest save(RegistrationRequest request) {
        return converter.toModel(repository.save(converter.toEntity(request)));
    }
}
